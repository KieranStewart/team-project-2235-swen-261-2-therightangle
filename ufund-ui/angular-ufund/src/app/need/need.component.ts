import { Component, Input } from '@angular/core';
import { Need } from '../need';
/*
 * This component is NOT a need object (need.ts is kind of that)
 * It is also NOT the thing that communicates needs
 * All it does is show a single need, which is done with a button
 * 
 * This component doesn't know anything about the Need Service.
 * It just knows about the Need that it was given.
*/
@Component({
  selector: 'need-button',
  templateUrl: './need.component.html',
  styleUrls: ['./need.component.css']
})
export class NeedComponent {

  @Input()
  need!: Need;
}
