import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CupboardComponent } from '../cupboard/cupboard.component';
import { BasketService } from '../basket.service';
import { Router } from '@angular/router';
import { NeedService } from '../need.service';
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
    // Should check that ammount was paid through external payment api thingy then add the ammount donated to each need in the basket
    if (this.payment())
    {
      for (const item of this.basketService.contents)
      {
        // item.progress = item.progress + + item.donationAmount;
        // item.donationAmount = 0;
        this.needService.updateNeed(item) // Causes duplicates and bad things to happen. (adding this comment fixed this (BEAN type error))
      }
      
      this.basketService.clear();
      this.router.navigate(['/confirmation/success']);
    }
    else
    {
      this.router.navigate(['/confirmation/fail']);
    }
  }

  payment(): boolean {
    return true;
  }
}
