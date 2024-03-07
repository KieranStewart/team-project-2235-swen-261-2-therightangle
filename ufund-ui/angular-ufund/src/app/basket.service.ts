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

  // clear(): void {
  //   for(let index = 0; index < this.contents.length; index++) {
  //     const element = this.contents[index];
  //     element.
  //   }
  //   this.contents = [];
  // }

  add(need: Need): void {
    need.inFundingBasket = true;
    this.contents.push(need);
  }

  remove(need: Need): void {
    var removeIndex = this.contents.indexOf(need);
    if(removeIndex != -1) {
      this.contents.splice(removeIndex, 1);
    }
    need.inFundingBasket = false;
  }

}
