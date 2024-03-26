import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CupboardComponent } from '../cupboard/cupboard.component';
import { BasketService } from '../basket.service';
import { Router } from '@angular/router';
import { NeedService } from '../need.service';
import { Need } from '../need';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})

export class CheckoutComponent implements OnInit{

  constructor(private basketService: BasketService, private router: Router, private needService: NeedService) { }
  
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
    // Should check that amount was paid through external payment api thingy then add the amount donated to each need in the basket
    if (this.makePayment())
    {
      for (let index = 0; index < this.basketService.contents.length; index++) {
        this.recordPayment(this.basketService.contents[index]);
      }
      
      this.basketService.clear();
      this.router.navigate(['/confirmation/success']);
    }
    else
    {
      this.router.navigate(['/confirmation/fail']);
    }
  }

  /**
   * Uses a third party API to perform the transaction
   * @returns If the payment is valid
   */
  makePayment(): boolean {
    return true;
  }

  private recordPayment(need: Need): void {
    need.progress += Number(need.donationAmount);
    this.needService.updateNeed(need); // Causes duplicates and bad things to happen. (adding this comment fixed this (BEAN type error))
  }
}
