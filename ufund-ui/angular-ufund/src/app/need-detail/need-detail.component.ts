import { Component, Input } from '@angular/core';
import { Need } from '../need';
import { BasketService } from '../basket.service';

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

  saveNeed(donationAmount:string): void{
    this.basketService.update(this.displayNeed,donationAmount);
  }

  /**
   * additional logic as necessary
   */
}
