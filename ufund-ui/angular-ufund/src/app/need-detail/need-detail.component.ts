import { Component, Input } from '@angular/core';
import { Need } from '../need';
import { BasketService } from '../basket.service';
import { VolunteerDate } from '../volunteer-date';
import { LoginService } from '../login.service';
import { TagManagerService } from '../tag-manager.service';
import { NeedService } from '../need.service';
import { Tag } from '../tag';
import { NeedCacheService } from '../need-cache.service';

/**
 * Shows the details for the selected need.
 */
@Component({
  selector: 'app-need-detail',
  templateUrl: './need-detail.component.html',
  styleUrls: ['./need-detail.component.css']
})
export class NeedDetailComponent {

  @Input() displayNeed!: Need | null;

  tagManagerContent: String[];
  hoverTag!: Tag;
  showTags = false;

  tagMessage = "All tags avaliable to be added to this need:"

  constructor(private basketService: BasketService, private tagManagerService: TagManagerService, private needService: NeedService, public loginService: LoginService, public needCacheService : NeedCacheService) {
    this.tagManagerContent = []
  }

  refresh() {
    this.displayNeed = this.needCacheService.selectedNeed;
  }

  addToFundingBasket(): void {
    if (this.displayNeed == null) {
      return;
    }
    this.basketService.add(this.displayNeed);
  }

  removeFromFundingBasket(): void {
    if (this.displayNeed == null) {
      return;
    }
    this.basketService.remove(this.displayNeed);
  }

  
  dateSelected(date: VolunteerDate): boolean {
    try {
      if(this.needCacheService.selectedNeed == null) {
        return false;
      }
      return this.needCacheService.selectedNeed.selectedVolunteerDates.indexOf(date, 0) != -1;
    } catch (error) {
      return false;
    }
    return false;
  }

  removeDate(date: VolunteerDate): void {
    if(this.needCacheService.selectedNeed == null) {
      console.log("Attempted to remove date from null displayNeed");
      return;
    }
    let newVolunteerDates: VolunteerDate[] = this.needCacheService.selectedNeed.selectedVolunteerDates;
    if (newVolunteerDates == undefined)
    {
      newVolunteerDates = [];
    }
    let index: number = newVolunteerDates.indexOf(date);
    if (index != -1) {
      newVolunteerDates.splice(index);
    } else {
      console.log("ERROR, VolunteerDate already removed, or not in dates array");
    }
    console.log("Date removed from cart");
    this.needCacheService.selectedNeed.selectedVolunteerDates = newVolunteerDates;
  };

  // Don't use this, directly bind the input to displayNeed.donationAmount.  It'll probably be easier.
  // You can use this if you want a save button instead of automatically linking to the input field.
  // saveNeed(donationAmount: number): void{
  //   this.displayNeed.donationAmount = donationAmount;
  // }

  /**
   * Checks if the transactions should be visible.
   * @returns true if admin, false otherwise
   */
  transactionListVisible(): boolean {
    if (this.loginService.userAccount != null)
      return this.loginService.userAccount.isAdmin;
    return false;
  }

  showTagList(): void {
    this.tagManagerContent = this.tagManagerService.getList();
    this.toggleShowTags();
  }

  toggleShowTags(): void {
    this.showTags = !this.showTags;
  }

  addTag(name: String): void {
    if (this.displayNeed == null) {
      return;
    }
    if (this.displayNeed.tags.indexOf(name) == -1) {
      this.tagMessage = name + " has been add to this need: " + this.displayNeed.name
      this.displayNeed.tags.push(name)
      this.needService.updateNeed(this.displayNeed).subscribe();
    } else {
      this.tagMessage = "This need already has this tag"
    }
  }

  removeTag(name: String): void {
    if (this.displayNeed == null) {
      return;
    }
    if (name == "admin") {
      this.tagMessage = "can't remove tag admin from this need because it's a permanent variable"
    } else {
      this.tagMessage = name + " has been remove from this need: " + this.displayNeed.name
      var index = this.displayNeed.tags.indexOf(name);
      this.displayNeed.tags.splice(index, 1);
      this.needService.updateNeed(this.displayNeed).subscribe();
    }
  }

  changeHoverTag(name: String): void {
    this.hoverTag = this.tagManagerService.getTag(name);
  }

  isAdmin(): boolean {
    if (this.loginService.userAccount != null)
      return this.loginService.userAccount.isAdmin
    return false;
  }
}
