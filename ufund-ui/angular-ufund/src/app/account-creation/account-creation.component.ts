import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Account } from '../account';
import { Tag } from '../tag';
import { LoginService } from '../login.service';
import { LoginComponent } from '../login/login.component';
import { TagManagerService } from '../tag-manager.service';
import { TagService } from '../tags.service';
import { HttpErrorResponse } from '@angular/common/http';


@Component({
  selector: 'app-account-creation',
  templateUrl: './account-creation.component.html',
  styleUrls: ['./account-creation.component.css']
})
export class AccountCreationComponent {
  errorMessage = '';
  submitted = false;
  newAccount!: Account;
  publicTag = this.tagManager.publicTag;
  private emailExtensions: Array<string> = ["@gmail.com", "@rit.edu", "@g.rit.edu"];

  constructor(private loginService: LoginService,private loginComponent: LoginComponent,private tagManager: TagManagerService) {}

  createAccount(usernameInput: string, passwordInput: string, passwordCheckInput: string, emailInput: string): void {
      var invalidEmailDomain: boolean;
      invalidEmailDomain = true;
      /* Checks all valid email extensions to see if the emailInput is valid */
      for (let index = 0; index < this.emailExtensions.length; index++) {
        const element = this.emailExtensions[index];
        if (emailInput.includes(element)) {
          invalidEmailDomain = false;
        }
      }

      if (usernameInput == '' || passwordInput == '' || passwordCheckInput == '' || emailInput == '') {
        this.errorMessage = "Please fill in all the boxes";
      } else if (emailInput[0] == '@') {
        this.errorMessage = "The email address starts with an @, which is invalid";
      } else if (passwordInput != passwordCheckInput){
        this.errorMessage = "Password doesn't match"
      } else if (invalidEmailDomain) {
        this.errorMessage = "Invalid email \n Must be a @gmail.com,@rit.edu, or a @g.rit.edu email address";
      } else {
        const that = this;
        this.publicTag = this.tagManager.publicTag;
        this.newAccount = {name: usernameInput, password: passwordInput, email: emailInput,tags: ["public"], isAdmin: false} as Account;
        this.loginService.searchAccount(usernameInput).subscribe({
          next(tempAccount) {
            if (tempAccount.toString() == ""){
              "That username is taken already serach method"
            }
          }
        })


        // make sure the account doesn't already exist
        this.loginService.addAccount(this.newAccount).subscribe({
          next(newAccount) {
            if(newAccount == undefined) {
              that.errorMessage = "That username is taken already";
            } else {
              that.newAccount = newAccount;
              that.loginComponent.toggleAccountCreationScreen();
              that.submitted = true;
              that.errorMessage = '';
              that.loginComponent.message = "Account created successfully! Log in to begin.";
            }
          }
        });        
      }
  }

  onSubmit() {
    this.submitted = true;
  }
}
