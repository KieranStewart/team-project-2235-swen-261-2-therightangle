import { Injectable } from '@angular/core';
import { Need } from './need';
import { NeedCacheService } from './need-cache.service';
import { CupboardComponent } from './cupboard/cupboard.component';

@Injectable({
  providedIn: 'root'
})
export class BasketService {

  contents: Need[];

  constructor() { 
    this.contents = [];
  }

  clear(): void {
    for(let index = 0; index < this.contents.length; index++) {
      const element = this.contents[index];
      element.inFundingBasket = false;
      element.donationAmount = 0;
    }
    this.contents = [];
  }

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

  refresh(cupboard : Need[]): void {
    for (let index = 0; index < cupboard.length; index++) {
      const element = cupboard[index];
      for (let jndex = 0; jndex < this.contents.length; jndex++) {
        if(this.contents[jndex].name == element.name) {
          this.contents[jndex] = cupboard[index];
        }
        
      }
    }
  }

}
