import { Component, Input } from '@angular/core';
import { NeedCacheService } from '../need-cache.service';
import { VolunteerDate } from '../volunteer-date';
import { NgFor } from '@angular/common';
@Component({
  selector: 'app-date',
  templateUrl: './date.component.html',
  styleUrls: ['./date.component.css']
})
export class  DateComponent {
  constructor(private cacheService: NeedCacheService) {}
  
  @Input()
  date!: VolunteerDate;

  select(): void {
    this.cacheService.selectedNeed.selectedVolunteerDates.push(this.date); // Add to array not set
  }
}
