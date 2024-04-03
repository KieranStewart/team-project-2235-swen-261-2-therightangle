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

  adminTag(): boolean{ //admin tag is present and it's the only one
    if(this.tagList.length==1 && this.tagList[0] == 'admin'){
      return true;
    }
    return false;
  }

  userIsAdmin(): boolean {//check user status (admin or not)
    return this.currentUser.isAdmin;
  }

  tagAndUser(): boolean{
    if(this.adminTag() && this.userIsAdmin()){
      return true;
    } if(!this.adminTag() && this.userIsAdmin()){
      return true;
    } if(!this.adminTag() && !this.userIsAdmin()){
      return true;
    } if(this.adminTag() && !this.userIsAdmin){
      return false;
    }
    return false;
  }
}
