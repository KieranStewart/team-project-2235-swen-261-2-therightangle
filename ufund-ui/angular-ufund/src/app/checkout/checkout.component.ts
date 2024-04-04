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
    need.progress += Number(need.donationAmount);
    this.needService.updateNeed(need).subscribe();
  }

  private recordHours(need: Need): void {
    for (let i = 0; i < need.selectedVolunteerDates.length; i++)
    {
      for (let j = 0; j < need.volunteerDates.length; j++)
      {
        if (need.volunteerDates[j].year == need.selectedVolunteerDates[i].year 
          && need.volunteerDates[j].month == need.selectedVolunteerDates[i].month 
          && need.volunteerDates[j].day == need.selectedVolunteerDates[i].day)
          { 
            need.volunteerDates[j].filled = true;
            break;
          }
      }
    }
    need.selectedVolunteerDates = [];
    this.needService.updateNeed(need).subscribe;
  }
}
