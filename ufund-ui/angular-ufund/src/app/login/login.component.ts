import { Component, Input, OnInit } from '@angular/core';
import { Account } from '../account';
import { LoginService } from '../login.service';
import { Router } from '@angular/router';
import { BasketService } from '../basket.service';
import { NeedCacheService } from '../need-cache.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  showAccountCreation = false;
  LoginSuccess = false;
  username ="";
  password ="";
  account!: Account;
  message = "";

  constructor(private loginService: LoginService, private router: Router, private basketService: BasketService, private needCacheService : NeedCacheService) {}

  login(username: string, password: string): void {
    this.message = "loading";
    this.username = username
    this.password = password
    const that = this;

    this.loginService.validateLogin(username, password)
    .subscribe({
      next(response) {
        that.message = response;
        // code that concerns the response should go here
        // putting something like "if success then turn green" outside of here will be weird and not work sometimes
        if (response == 'Login successful') {
          that.router.navigate(['/']);
          that.basketService.clear();
          that.needCacheService.selectedNeed = null;
        }
      }
    });
  }
  toggleAccountCreationScreen(): void {
    this.showAccountCreation = !this.showAccountCreation;
  }

}
 