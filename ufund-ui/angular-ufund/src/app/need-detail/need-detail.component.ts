import { Component, Input } from '@angular/core';
import { Need } from '../need';
import { BasketService } from '../basket.service';
import { VolunteerDate } from '../volunteer-date';

/**
 * Shows the details for the selected need.
 */
@Component({
  selector: 'app-need-detail',
  templateUrl: './need-detail.component.html',
  styleUrls: ['./need-detail.component.css']
})
export class NeedDetailComponent {
  constructor(private basketService: BasketService) {
  }

  @Input()
  displayNeed!: Need;

  addToFundingBasket(): void {
    this.basketService.add(this.displayNeed);
  }

  removeFromFundingBasket(): void {
    this.basketService.remove(this.displayNeed);
  }

  dateSelected(date: VolunteerDate): boolean {
    try {
      return this.displayNeed.selectedVolunteerDates.indexOf(date, 0) != -1;
    } catch (error) {
      return false;
    }
    
  }

  // Don't use this, directly bind the input to displayNeed.donationAmount.  It'll probably be easier.
  // You can use this if you want a save button instead of automatically linking to the input field.
  // saveNeed(donationAmount: number): void{
  //   this.displayNeed.donationAmount = donationAmount;
  // }

  /**
   * additional logic as necessary
   */
}
