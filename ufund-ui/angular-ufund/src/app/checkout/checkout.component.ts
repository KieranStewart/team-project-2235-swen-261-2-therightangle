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

  constructor(public basketService: BasketService, private router: Router, private needService: NeedService, private transactionService: TransactionService, private cupboardComponent: CupboardComponent, private needCacheService: NeedCacheService) { }
  
  total: number = 0;
  total_h: number = 0;

  ngOnInit(): void {
    this.getTotal();
  }

  displayCheckout(): boolean {
    this.getTotal();
    return this.total + this.total_h > 0;
  }

  getTotal(): void {
    this.total = 0;
    this.total_h = 0;
    for (let item of this.basketService.contents)
    {
      if (item.type == "VOLUNTEER") {
        this.total_h = this.total_h + +this.getVolunteerHours(item);
      }
      else if (item.type == "DONATION"){
        this.total = this.total + parseFloat(item.donationAmount.toString());
      }
      else
      {
        console.log("One of the items in the cart has no type!");
      }
    }
  }
  
  checkout(): void {
    // Should check that amount was paid through external payment api thingy then add the amount donated to each need in the basket
    if (this.makePayment())
    {
      for (let index = 0; index < this.basketService.contents.length; index++) {
        if (this.basketService.contents[index].type == "VOLUNTEER")
          this.recordHours(this.basketService.contents[index]);
        else
          this.recordPayment(this.basketService.contents[index]);
        console.log("Checkout Recorded:" + this.basketService.contents[index].name);
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

  private getVolunteerHours(item: Need): number {
    let out:number = 0;
    for (let date_item of item.volunteerDates)
      {
        if (date_item.filled)
        {
          out ++;
        }
      }
    return out;
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

  areDonationsValid(): boolean {
    if(Number.isNaN(this.total)) {
      return false;
    }
    for (let index = 0; index < this.basketService.contents.length; index++) {
      const element = this.basketService.contents[index];
      if(element.type == "DONATION" && element.donationAmount <= 0) {
        return false;
      }
    }
    return true;
  }

  areVolunteerDatesValid(): boolean {
    return true; // TODO
  }

  private recordHours(need: Need): void {
    for (let i = 0; i < need.selectedVolunteerDates.length; i++)
    {
      for (let j = 0; j < need.volunteerDates.length; j++)
      {
        if (need.volunteerDates[j].year == need.selectedVolunteerDates[i].year 
          && need.volunteerDates[j].month == need.selectedVolunteerDates[i].month 
          && need.volunteerDates[j].day == need.selectedVolunteerDates[i].day && need.volunteerDates[j].filled == false)
          { 
            console.log("Volunteer Date " + need.volunteerDates[j].day + " is set to " + need.selectedVolunteerDates[i].day);
            
            need.volunteerDates[j].filled = true;
            break;
          }
      }
    }
    console.log(need.volunteerDates + ":" + need.selectedVolunteerDates);
    
    need.selectedVolunteerDates = [];
    this.needService.updateNeed(need).subscribe();
  }
}
