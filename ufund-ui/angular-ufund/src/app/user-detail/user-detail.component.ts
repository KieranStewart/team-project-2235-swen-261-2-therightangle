import { Component, Input } from '@angular/core';
import { Account } from '../account';
import { TagManagerService } from '../tag-manager.service';
import { LoginService } from '../login.service';


@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent {
  @Input()
  displayAccount!: Account;
  tagManagerContent: String[];
  showTags = false;
  tagMessage = ""

  constructor(private tagManagerService: TagManagerService, private loginService: LoginService) {
    this.tagManagerContent = []
    loginService.getAccount("admin").subscribe(displayAccount => this.displayAccount = displayAccount);
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
      this.tagMessage = ""
      this.displayAccount.tags.push(name)
      this.loginService.updateAccount(this.displayAccount).subscribe();
    } else {
      this.tagMessage = "This need already has this tag"
    }
  }

  removeTag(name: String): void {
    if (name == "public") {
      this.tagMessage = "can't remove tag " + name + " from this need because it's a permanent variable"
    } else {
      this.tagMessage = ""
      var index = this.displayAccount.tags.indexOf(name);
      this.displayAccount.tags.splice(index, 1);
      this.loginService.updateAccount(this.displayAccount).subscribe();
    }
  }


}
