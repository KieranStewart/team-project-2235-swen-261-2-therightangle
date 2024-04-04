import { Component, Input } from '@angular/core';
import { NeedCacheService } from '../need-cache.service';
import { VolunteerDate } from '../volunteer-date';
import { NgFor } from '@angular/common';
import { BasketService } from '../basket.service';
import { Need } from '../need';
@Component({
  selector: 'app-date',
  templateUrl: './date.component.html',
  styleUrls: ['./date.component.css']
})
export class  DateComponent {
  constructor(private cacheService: NeedCacheService, private basketService: BasketService) {}
  
  @Input()
  date!: VolunteerDate;
  @Input()
  need!: Need;

  select(): void {
    // let temp_need: Need = this.cacheService.selectedNeed;
    // temp_need.selectedVolunteerDates.push(this.date);
    // this.basketService.edit(this.cacheService.selectedNeed, temp_need);
    // this.cacheService.selectedNeed = temp_need;
    this.addSelectedDate(this.date);
  }

  private addSelectedDate(date:VolunteerDate): void {
    let newVolunteerDates: VolunteerDate[] = this.need.selectedVolunteerDates;
    if (newVolunteerDates == undefined)
    {
      newVolunteerDates = [];
    }
    console.log(newVolunteerDates);
    newVolunteerDates.push(date);
    console.log(newVolunteerDates);
    this.need.selectedVolunteerDates = newVolunteerDates;
  };
}
