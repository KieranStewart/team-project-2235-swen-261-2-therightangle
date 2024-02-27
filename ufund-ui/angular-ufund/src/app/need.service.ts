import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Need } from './need';


@Injectable({ providedIn: 'root' })
export class NeedService {

  private cupboardUrl = 'http://localhost:8080/cupboard';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient) { }

  /** GET needs from the server */
  getCupboard(): Observable<Need[]> {
    return this.http.get<Need[]>(this.cupboardUrl)
      .pipe(
        //tap(_ => this.log('fetched cupboard')),
        catchError(this.handleError<Need[]>('getCupboard', []))
      );
  }

  /** GET need by name. Return `undefined` when name not found */
  getNeedNo404<Data>(name: String): Observable<Need> {
    const url = `${this.cupboardUrl}/?name=${name}`;
    return this.http.get<Need[]>(url)
      .pipe(
        map(cupboard => cupboard[0]), // returns a {0|1} element array
        // tap(h => { // TODO deprecated
        //   const outcome = h ? 'fetched' : 'did not find';
        //   this.log(`${outcome} need name=${name}`);
        // }),
        catchError(this.handleError<Need>(`getNeed name=${name}`))
      );
  }

  /** GET need by name. Will 404 if name not found */
  getNeed(name: String): Observable<Need> {
    const url = `${this.cupboardUrl}/${name}`;
    return this.http.get<Need>(url).pipe(
      //tap(_ => this.log(`fetched need name=${name}`)),
      catchError(this.handleError<Need>(`getNeed name=${name}`))
    );
  }

  /* GET cupboard whose name contains search term */
  searchCupboard(term: string): Observable<Need[]> {
    if (!term.trim()) {
      // search not found? return empty need array.
      return of([]); // TODO: deprecated
    }
    return this.http.get<Need[]>(`${this.cupboardUrl}/?name=${term}`).pipe(
      // tap(x => x.length ?
      //    this.log(`found needs matching "${term}"`) :
      //    this.log(`no needs matching "${term}"`)),
      catchError(this.handleError<Need[]>('searchCupboard', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new need to the server */
  addNeed(need: Need): Observable<Need> {
    return this.http.post<Need>(this.cupboardUrl, need, this.httpOptions).pipe(
      // tap((newNeed: Need) => this.log(`added need w/ name=${newNeed.name}`)),
      catchError(this.handleError<Need>('addNeed'))
    );
  }

  /** DELETE: delete the need from the server */
  deleteNeed(name: String): Observable<Need> {
    const url = `${this.cupboardUrl}/${name}`;

    return this.http.delete<Need>(url, this.httpOptions).pipe(
      // tap(_ => this.log(`deleted need name=${name}`)),
      catchError(this.handleError<Need>('deleteNeed'))
    );
  }

  /** PUT: update the need on the server */
  updateNeed(need: Need): Observable<any> {
    return this.http.put(this.cupboardUrl, need, this.httpOptions).pipe(
      // tap(_ => this.log(`updated need name=${need.name}`)),
      catchError(this.handleError<any>('updateNeed'))
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

      // TODO: communicate error to the user
      // this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T); // TODO deprecated and mysterious
    };
  }

  // If we ever want to show system messages to the user
  // /** Log a NeedService message with the MessageService */
  // private log(message: string) {
  //   this.messageService.add(`NeedService: ${message}`);
  // }
  
}
