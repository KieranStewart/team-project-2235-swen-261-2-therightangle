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

  currentUser: Account | null = this.loginService.userAccount

  userIsAdmin(): boolean {
    if (this.currentUser != null) { return this.currentUser.isAdmin; }
    return false;
  }

  logout(): void {
    this.loginService.userAccount = null;
    location.reload()
  }
  refresh(): void {
    location.reload()
  }
}
