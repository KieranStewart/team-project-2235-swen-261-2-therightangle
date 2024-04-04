import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { ActivatedRoute } from '@angular/router';
import { NeedCacheService } from '../need-cache.service';
import { take } from 'rxjs';
import { HomeViewComponent } from '../home-view/home-view.component';
import { BasketService } from '../basket.service';
import { NeedDetailComponent } from '../need-detail/need-detail.component';


@Component({
  selector: 'app-cupboard',
  templateUrl: './cupboard.component.html',
  styleUrls: ['./cupboard.component.css']
})
export class CupboardComponent implements OnInit{
  cupboard: Need[] = []; 
  title = 'angular-ufund';

  constructor(private needService: NeedService, public needCacheService: NeedCacheService, private searchComponent : HomeViewComponent, private basketService : BasketService, private needDetailComponent : NeedDetailComponent) { }

  ngOnInit(): void {
    this.getCupboard();
  }

  getCupboard(): void {
    const that = this;
    this.needService.getCupboard().pipe(take(1))
    .subscribe({
      next(newCupboard) {
          that.cupboard = newCupboard;
          for (let index = 0; index < that.cupboard.length; index++) {
            const element = that.cupboard[index];
            if(that.needCacheService.selectedNeed != null && element.name == that.needCacheService.selectedNeed.name) {
              that.cupboard[index].inFundingBasket = that.needCacheService.selectedNeed.inFundingBasket;
              that.cupboard[index].donationAmount = that.needCacheService.selectedNeed.donationAmount;
              that.needCacheService.selectedNeed = that.cupboard[index];
              that.needDetailComponent.displayNeed = that.cupboard[index];
            }
          }
          that.searchComponent.search(that.searchComponent.searchTerm);
          that.basketService.refresh(that.cupboard);
          that.needDetailComponent.refresh();
      }
    });
  }
}
