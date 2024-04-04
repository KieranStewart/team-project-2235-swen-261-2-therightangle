import { Component, Input } from '@angular/core';
import { Need } from '../need';
import { NeedCacheService } from '../need-cache.service';
import { LoginService } from '../login.service';
import { Account } from '../account';
import { TagManagerService } from '../tag-manager.service';

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

  constructor(
    private cacheService: NeedCacheService,
    private loginService: LoginService,
    private tagManagerService: TagManagerService) { }

  @Input()
  need!: Need;

  currentUser: Account = this.loginService.userAccount
  tagList: String[] = this.tagManagerService.getList();

  showDetails(): void {
    this.cacheService.selectedNeed = this.need;
  }

  canUserSeeNeed(thisNeed : Need): boolean {
    if(thisNeed == null) {
      return false;
    }
    for (let index = 0; index < thisNeed.tags.length; index++) {
      const element = thisNeed.tags[index];
      if(this.loginService.userAccount.tags.includes(element)) {
        return true;
      }
    }
    return false;
  }
}
