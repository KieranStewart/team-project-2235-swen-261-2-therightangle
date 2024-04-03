import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CupboardComponent } from '../cupboard/cupboard.component';
import { BasketService } from '../basket.service';
import { Router } from '@angular/router';
import { NeedService } from '../need.service';
import { Need } from '../need';
import { TransactionService } from '../transaction.service';
import { Transaction } from '../transaction';
import { take } from 'rxjs';
import { NeedCacheService } from '../need-cache.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})

export class CheckoutComponent implements OnInit{

  constructor(private basketService: BasketService, private router: Router, private needService: NeedService, private transactionService: TransactionService, private cupboardComponent: CupboardComponent, private needCacheService: NeedCacheService) { }
  
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
    const that = this;
    need.progress += Number(need.donationAmount);
    this.needService.updateNeed(need).pipe(take(1)).subscribe();
    let transaction: Transaction;
    transaction = {
      amount: need.donationAmount,
      needName: need.name
    };
    // If you don't like and subscribe, it won't get any views (TypeScript moment)
    this.transactionService.addTransaction(transaction).pipe(take(1)).subscribe({
      next(value) {
          that.cupboardComponent.getCupboard(); // update cupboard view
          that.needCacheService.selectedNeed = need;
      },
    });
  }
}
