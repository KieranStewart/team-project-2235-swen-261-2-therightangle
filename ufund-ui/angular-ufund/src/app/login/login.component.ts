import { Component, Input, OnInit } from '@angular/core';
import { Account } from '../account';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{
  showAccountCreation = false;
  LoginSuccess = false;
  username ="";
  password ="";
  account!: Account;
  message = "";

  constructor(private loginService: LoginService){}

login(username:string,password:string): void{
  this.message = "loading";
  this.username = username
  this.password = password
  this.loginService.getAccount(this.username)
  .subscribe(account => this.account = account);
  if (this.account.name == this.username){
    if (this.account.password == this.password){
        this.LoginSuccess = true;
        this.message = "Login Successful";
    } else {
      this.message = "Incorrect Password"
    }
  } else {
    this.message = "No user with that username"
  }
}

toggleAccountCreationScreen(): void{
  this.showAccountCreation = !this.showAccountCreation;
}

}
 