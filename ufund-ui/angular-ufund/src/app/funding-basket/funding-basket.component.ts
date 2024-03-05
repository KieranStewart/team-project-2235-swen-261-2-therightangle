import { Component } from '@angular/core';
import { BasketService } from '../basket.service';

@Component({
  selector: 'app-funding-basket',
  templateUrl: './funding-basket.component.html',
  styleUrls: ['./funding-basket.component.css']
})
export class FundingBasketComponent {
  constructor(private basket: BasketService) {}
}
