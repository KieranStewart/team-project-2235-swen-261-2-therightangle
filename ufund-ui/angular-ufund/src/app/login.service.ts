import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, asyncScheduler, scheduled } from 'rxjs';
import { catchError, first, map, tap } from 'rxjs/operators';
import { Account } from './account';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  userAccount!: Account;
  private accountUrl = 'http://localhost:8080/account';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient) { }

  /** GET Accounts from the server */
  getAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(this.accountUrl)
      .pipe(
        tap(_ => this.log('fetched account')), // tap is deprecated, maybe replace
        catchError(this.handleError<Account[]>('getAccounts', []))
      );
  }

  /** 
   * GET Account by name. Does not return an observable that emits `undefined` when name not found: other methods do that.
   * When name isn't found, it actually returns a hollow shell of an observable
   */
  getAccountNo404<Data>(name: string): Observable<Account> {
    const url = `${this.accountUrl}/?name=${name}`;
    return this.http.get<Account[]>(url)
      .pipe(
        map(account => account[0]), // returns a {0|1} element array
        tap(h => { // notice: deprecated
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} Account name=${name}`);
        }),
        catchError(this.handleError<Account>(`getAccount name=${name}`))
      );
  }

  /** GET Account by name. Will 404 if name not found */
  getAccount(name: string): Observable<Account> {
    const url = `${this.accountUrl}/${name}`;
    return this.http.get<Account>(url).pipe(
      tap(_ => this.log(`fetched Account name=${name}`)), // notice: deprecated
      catchError(this.handleError<Account>(`getAccount name=${name}`))
    );
  }

  /* GET account whose name contains search term */
  searchAccount(term: string): Observable<Account[]> {
    if (!term.trim()) {
      // search not found? return empty Account array.
      return scheduled([[]], asyncScheduler);
    }
    return this.http.get<Account[]>(`${this.accountUrl}/?name=${term}`).pipe(
      tap(x => x.length ? // notice: deprecated
         this.log(`found Accounts matching "${term}"`) :
         this.log(`no Accounts matching "${term}"`)),
      catchError(this.handleError<Account[]>('searchaccount', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new Account to the server */
  addAccount(Account: Account): Observable<Account> {
    return this.http.post<Account>(this.accountUrl, Account, this.httpOptions).pipe(
      tap((newAccount: Account) => this.log(`added Account w/ name=${newAccount.name}`)), // notice: deprecated
      catchError((this.handleError<Account>('addAccount')))
      )
  }

  /** DELETE: delete the Account from the server */
  deleteAccount(name: string): Observable<Account> {
    const url = `${this.accountUrl}/${name}`;

    return this.http.delete<Account>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted Account name=${name}`)), // notice: deprecated
      catchError(this.handleError<Account>('deleteAccount'))
    );
  }

  /** PUT: update the Account on the server */
  updateAccount(Account: Account): Observable<any> {
    return this.http.put(this.accountUrl, Account, this.httpOptions).pipe(
      tap(_ => this.log(`updated Account name=${Account.name}`)), // notice: deprecated
      catchError(this.handleError<any>('updateAccount'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return scheduled([result as T], asyncScheduler);
    };
  }

  /** 
   * Log a LoginService message.  If we want to communicate this to the user, 
   * we will want to change this from console logging to a component.
   * We probably want to communicate with the user somehow if an error occurs.
   */
  private log(message: string) {
    console.log(message);
  }
  
  /**
   * Returns whether or not the log in is valid, as an error message.
   * If TypeScript has enums, replace string with an enum.
   * @param username 
   * @param password 
   */
  validateLogin(username: string, password: string): Observable<string> {
    if(username == "") {
      return new Observable((subscriber) => {
        subscriber.next("Please enter your username"); 
      });
    }    

    if(password == "") {
      return new Observable((subscriber) => {
        subscriber.next("Please enter your password"); 
      });
    }

    const that = this;
    var response: Observable<string>;

    response = new Observable((subscriber) => {
      that.getAccount(username) // This probably should be No404 so no errors pop up in console.  However...
      .subscribe({
        next(account) {
          console.log('Server responded to the request for an account with ' + account);
          if(account == undefined) { // ... see right here?  No404 doesn't return undefined when nothing is found.  When nothing is found, I have no idea what it returns!
                                    // later, we'll need to figure out what it actually returns and replace "undefined" with whatever that thingie is
                                    // I did try printing its return, but it is an object, so it didn't actually tell me anything.
            subscriber.next("Account not found"); 
            return;
          }
          if(password == account.password) {
            subscriber.next("Login successful");
            that.userAccount = account;
          } else {
            subscriber.next("Incorrect password"); 
          }
        },
        error(err) {
          console.error('something wrong occurred: ' + err);
          subscriber.next("Internal error"); 
        }
      });
    });
    return response;
  }


}

