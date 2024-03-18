import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CupboardComponent } from '../cupboard/cupboard.component';
import { BasketService } from '../basket.service';
import { FundingBasketComponent } from '../funding-basket/funding-basket.component';
@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})

export class CheckoutComponent implements OnInit{

  constructor(private basketService: BasketService) { }
  
  total: number = 0;

  ngOnInit(): void {
    this.getTotal();
  }

  getTotal(): void {
    for (const item of this.basketService.contents)
    {
      this.total = this.total + +item.donationAmount;
    }
  }
  
  checkout(): void {
    // Should check that ammount was paid through external payment api thingy then add the ammount donated to each need in the basket
    if (this.payment())
    {
      for (const item of this.basketService.contents)
      {
        item.progress = item.donationAmount;
        item.donationAmount = 0;
      }
      this.basketService.clear();
    }
  }

  payment(): boolean {
    return true;
  }
}
