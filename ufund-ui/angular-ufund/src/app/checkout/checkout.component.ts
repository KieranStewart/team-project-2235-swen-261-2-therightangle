import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CupboardComponent } from '../cupboard/cupboard.component';
import { BasketService } from '../basket.service';
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
      this.total += item.donationAmount;
    }
  }
}
