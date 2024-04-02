import { Component, Input } from '@angular/core';
import { Need } from '../need';
import { NeedCacheService } from '../need-cache.service';
/*
 * This component is NOT a need object (need.ts is kind of that)
 * It is also NOT the thing that communicates needs
 * All it does is show a single need, which is done with a button
 * 
 * This component doesn't know anything about the Need Service.
 * It just knows about the Need that it was given.
 * To use this, go like
 * <need-button [need]="theNeedVariableIWannaPutHere"></need-button>
*/
@Component({
  selector: 'need-button',
  templateUrl: './need.component.html',
  styleUrls: ['./need.component.css']
})
export class NeedComponent {

  constructor(private cacheService: NeedCacheService) { }

  @Input()
  need!: Need;

  showDetails(): void {
    this.cacheService.selectedNeed = this.need;
  }
}
