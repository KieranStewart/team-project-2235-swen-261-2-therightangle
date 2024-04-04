import { Component } from '@angular/core';
import { NeedCacheService } from './need-cache.service';
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import { AccountCasheService } from './account-cashe.service';
import { LoginService } from './login.service';
import { Account } from './account';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(
    public needCacheService: NeedCacheService,
    public accountCasheService: AccountCasheService,
    public loginService: LoginService) { }

  title = 'angular-ufund';
  
  currentUser: Account = this.loginService.userAccount

  userIsAdmin(): boolean{
    return this.currentUser.isAdmin;
  }
}
