import { Component } from '@angular/core';
import { BasketService } from '../basket.service';
import { Need } from '../need';

@Component({
  selector: 'app-funding-basket',
  templateUrl: './funding-basket.component.html',
  styleUrls: ['./funding-basket.component.css']
})
export class FundingBasketComponent {

  fundingBasket: Need[] = this.basket.contents;

  constructor(private basket: BasketService) {}

}
