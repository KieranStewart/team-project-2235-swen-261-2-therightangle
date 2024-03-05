import { Injectable } from '@angular/core';
import { Need } from './need';

@Injectable({
  providedIn: 'root'
})
export class BasketService {

  contents: Need[];

  constructor() { 
    this.contents = [];
  }

}
