import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, asyncScheduler, scheduled } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Tag } from './tag';

/**
 * Any method that sends an HTTP request must have its return value
 * subscribed to for that method to actually execute!
 */
@Injectable({ providedIn: 'root' })
export class TagService {

  private tagUrl = 'http://localhost:8080/tag';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient) { }

  /** GET tags from the server */
  getTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>(this.tagUrl)
      .pipe(
        tap(_ => this.log('fetched tag')), // tap is deprecated, maybe replace
        catchError(this.handleError<Tag[]>('getTag', []))
      );
  }

  /** GET tag by name. Return `undefined` when name not found */
  getTagNo404<Data>(name: String): Observable<Tag> {
    const url = `${this.tagUrl}/?name=${name}`;
    return this.http.get<Tag[]>(url)
      .pipe(
        map(tag => tag[0]), // returns a {0|1} element array
        tap(h => { // notice: deprecated
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} tag name=${name}`);
        }),
        catchError(this.handleError<Tag>(`getTag name=${name}`))
      );
  }

  /** GET tag by name. Will 404 if name not found */
  getTag(name: String): Observable<Tag> {
    const url = `${this.tagUrl}/${name}`;
    return this.http.get<Tag>(url).pipe(
      tap(_ => this.log(`fetched tag name=${name}`)), // notice: deprecated
      catchError(this.handleError<Tag>(`getTag name=${name}`))
    );
  }

  /* GET tag whose name contains search term */
  searchTag(term: string): Observable<Tag[]> {
    if (!term.trim()) {
      // search not found? return empty tag array.
      return scheduled([[]], asyncScheduler);
    }
    return this.http.get<Tag[]>(`${this.tagUrl}/?name=${term}`).pipe(
      tap(x => x.length ? // notice: deprecated
         this.log(`found tags matching "${term}"`) :
         this.log(`no tags matching "${term}"`)),
      catchError(this.handleError<Tag[]>('searchTag', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new tag to the server */
  addTag(tag: Tag): Observable<Tag> {
    return this.http.post<Tag>(this.tagUrl, tag, this.httpOptions).pipe(
      tap((newTag: Tag) => this.log(`added tag w/ name=${newTag.name}`)), // notice: deprecated
      catchError(this.handleError<Tag>('addTag'))
    );
  }

  /** DELETE: delete the tag from the server */
  deleteTag(name: String): Observable<Tag> {
    const url = `${this.tagUrl}/${name}`;

    return this.http.delete<Tag>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted tag name=${name}`)), // notice: deprecated
      catchError(this.handleError<Tag>('deleteTag'))
    );
  }

  /** PUT: update the tag on the server */
  updateTag(tag: Tag): Observable<any> {
    return this.http.put(this.tagUrl, tag, this.httpOptions).pipe(
      tap(_ => this.log(`updated tag name=${tag.name}`)), // notice: deprecated
      catchError(this.handleError<any>('updateTag'))
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
   * Log a TagService message.  If we want to communicate this to the user, 
   * we will want to change this from console logging to a component.
   * We probably want to communicate with the user somehow if an error occurs.
   */
  private log(message: string) {
    console.log(message);
    //this.messageService.add(`TagService: ${message}`);
  }
  
}
