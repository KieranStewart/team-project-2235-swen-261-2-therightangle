import { Component, OnInit } from '@angular/core';
import { Account } from '../account';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{

  showAccountCreation = true;
  username ="";
  password ="";


login(username:string,password:string): void{
  this.username = username
  this.password = password
}


goToAccountCreationScreen(): void{
  this.showAccountCreation = true;
}

}
 