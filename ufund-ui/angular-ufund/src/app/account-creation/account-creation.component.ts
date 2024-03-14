import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Account } from '../account';

@Component({
  selector: 'app-account-creation',
  templateUrl: './account-creation.component.html',
  styleUrls: ['./account-creation.component.css']
})
export class AccountCreationComponent {
  errorMessage = '';
  
  submitted = false;

  create(usernameInput:string,passwordInput:string,passwordCheckInput:string,emailInput:string):void{
      if (usernameInput == '' || passwordInput == '' || passwordCheckInput == '' || emailInput == ''){
        this.errorMessage = "Please fill in all the boxes"
      } else if (passwordInput != passwordCheckInput){
        this.errorMessage = "Password doesn't match"
      } else if (!emailInput.includes("@gmail.com") && !emailInput.includes("rit.edu") && !emailInput.includes("@g.rit.edu") ){
        this.errorMessage = "Invalid email \n Must be a @gmail.com,@rit.edu, or a @g.rit.edu email address"
      } else {
        this.submitted = true;
        this.errorMessage = '';
      }
  }

  onSubmit(){
    this.submitted = true;
  }
}
