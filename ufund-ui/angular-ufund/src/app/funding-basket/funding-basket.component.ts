import { Component } from '@angular/core';
import { BasketService } from '../basket.service';
import { Need } from '../need';
import { NeedService } from '../need.service';

@Component({
  selector: 'app-funding-basket',
  templateUrl: './funding-basket.component.html',
  styleUrls: ['./funding-basket.component.css']
})
export class FundingBasketComponent {
  fundingBasket: Need[] = this.basketService.contents;
  message = "";

  constructor(
    private basketService: BasketService, 
    private needService: NeedService,) {}


  submitFundingBasket(): void{
    const that = this;

    this.fundingBasket.forEach(donation => {
      this.needService.updateNeed(donation);
    });
    this.basketService.clear();
    this.message = "submitted";
  }
}
