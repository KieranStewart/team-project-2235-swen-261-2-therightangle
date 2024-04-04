import { Component, Input } from '@angular/core';
import { Account } from '../account';
import { TagManagerService } from '../tag-manager.service';
import { LoginService } from '../login.service';
import { Tag } from '../tag';
import { NONE_TYPE } from '@angular/compiler';
import { take } from 'rxjs';


@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent {
  @Input()
  displayAccount!: Account;
  tagManagerContent: String[];
  hoverTag!: Tag;
  showTags = false;
  tagMessage = "All tags avaliable to be added to this need:"

  constructor(private tagManagerService: TagManagerService, private loginService: LoginService) {
    this.tagManagerContent = []
    loginService.getAccount("admin").pipe(take(1)).subscribe(displayAccount => this.displayAccount = displayAccount);
  }

  showTagList(): void {
    this.tagManagerContent = this.tagManagerService.getList();
    this.toggleShowTags();
  }

  toggleShowTags(): void {
    this.showTags = !this.showTags;
  }

  addTag(name: String): void {
    if (this.displayAccount.tags.indexOf(name) == -1) {
      this.tagMessage = name + " has been add to this account: " + this.displayAccount.name
      this.displayAccount.tags.push(name)
      this.loginService.updateAccount(this.displayAccount).subscribe();
    } else {
      this.tagMessage = "This need already has this tag"
    }
  }

  removeTag(name: String): void {
    if (name == "public") {
      this.tagMessage = "can't remove tag " + name + " from this need because it's a permanent variable"
    } else if (name == "admin" && this.displayAccount.name == "admin") {
      this.tagMessage = "can't remove admin tag from this admin account"
    } else {
      this.tagMessage = name + " has been remove from this account: " + this.displayAccount.name
      var index = this.displayAccount.tags.indexOf(name);
      this.displayAccount.tags.splice(index, 1);
      this.loginService.updateAccount(this.displayAccount).subscribe();
    }
  }

  changeHoverTag(name: String): void {
    this.hoverTag = this.tagManagerService.getTag(name);
  }
}
