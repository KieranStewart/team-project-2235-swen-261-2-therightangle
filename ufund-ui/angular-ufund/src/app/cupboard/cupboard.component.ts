import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { ActivatedRoute } from '@angular/router';
import { NeedCacheService } from '../need-cache.service';
import { take } from 'rxjs';
import { HomeViewComponent } from '../home-view/home-view.component';


@Component({
  selector: 'app-cupboard',
  templateUrl: './cupboard.component.html',
  styleUrls: ['./cupboard.component.css']
})
export class CupboardComponent implements OnInit {
  cupboard: Need[] = [];
  title = 'angular-ufund';

  constructor(private needService: NeedService, public needCacheService: NeedCacheService, private searchComponent: HomeViewComponent) { }

  ngOnInit(): void {
    this.getCupboard();
  }

  getCupboard(): void {
    const that = this;
    this.needService.getCupboard().pipe(take(1))
      .subscribe({
        next(cupboard) {
          that.cupboard = cupboard;
          that.searchComponent.search(that.searchComponent.searchTerm);
          for (let index = 0; index < that.cupboard.length; index++) {
            const element = that.cupboard[index];
            if (that.needCacheService.selectedNeed != null && element.name == that.needCacheService.selectedNeed.name) {
              that.needCacheService.selectedNeed = element;
            }
          }
        }
      });
  }
}
