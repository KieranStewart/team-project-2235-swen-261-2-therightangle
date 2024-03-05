import { Injectable } from '@angular/core';
import { Need } from './need';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BasketService {

  private contents: Need[];

  constructor() { 
    this.contents = [];
  }

  // getContents(): Observable<Need> {
  //   return // create an observable that updates as needs are added and removed from the basket.
  //   // Don't do that if there is a better way (can you just link this service using binding or something?)
  // }
}
