import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, asyncScheduler, catchError, map, scheduled, tap } from 'rxjs';
import { Transaction } from './transaction';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  
  private transactionUrl = 'http://localhost:8080/transactions';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient) { }

  /** GET transactions from the server */
  getAllTransactions(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(this.transactionUrl)
      .pipe(
        tap(_ => this.log('fetched all transactions')), // tap is deprecated, maybe replace
        catchError(this.handleError<Transaction[]>('getAllTransactions', []))
      );
  }

  /** GET transactions by need name. Return `undefined` when name not found */
  getTransactionsNo404<Data>(name: string): Observable<Transaction[]> {
    const url = `${this.transactionUrl}/?name=${name}`;
    return this.http.get<Transaction[]>(url)
      .pipe(
        map(transactions => transactions), // returns ????????
        tap(h => { // notice: deprecated
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} transactions for need name=${name}`);
        }),
        catchError(this.handleError<Transaction[]>(`getTransactions name=${name}`))
      );
  }

  /** GET transaction by need name. Will 404 if name not found */
  getTransactions(name: string): Observable<Transaction[]> {
    const url = `${this.transactionUrl}/${name}`;
    return this.http.get<Transaction[]>(url).pipe(
      tap(_ => this.log(`fetched transactions for need name=${name}`)), // notice: deprecated
      catchError(this.handleError<Transaction[]>(`getTransactions name=${name}`))
    );
  }

  //////// Save methods //////////

  /** POST: add a new transaction to the server */
  addTransaction(transaction: Transaction): Observable<Transaction> {
    return this.http.post<Transaction>(this.transactionUrl, transaction, this.httpOptions).pipe(
      tap((newTransaction: Transaction) => this.log(`added transaction w/ needName=${newTransaction.needName}`)), // notice: deprecated
      catchError(this.handleError<Transaction>('addTransaction'))
    );
  }

  /** DELETE: delete transactions for this need from the server */
  deleteTransactionsFor(name: string): Observable<Transaction> {
    const url = `${this.transactionUrl}/${name}`;

    return this.http.delete<Transaction>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted transactions for need name=${name}`)), // notice: deprecated
      catchError(this.handleError<Transaction>('deleteTransactionsFor'))
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
   * Log message to console
   */
  private log(message: string) {
    console.log(message);
    //this.messageService.add(`TransactionService: ${message}`);
  }
  
}
