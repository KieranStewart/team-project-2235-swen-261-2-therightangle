import { Component, Input } from '@angular/core';
import { Need } from '../need';
import { BasketService } from '../basket.service';

@Component({
  selector: 'app-need-detail',
  templateUrl: './need-detail.component.html',
  styleUrls: ['./need-detail.component.css']
})
export class NeedDetailComponent {

  constructor(private basketService: BasketService) {}

  @Input()
  displayNeed!: Need;

  addToFundingBasket(): void {
    this.basketService.add(this.displayNeed);
  }

  removeFromFundingBasket(): void {
    this.basketService.remove(this.displayNeed);
  }

  /**
   * additional logic as necessary
   */
}
