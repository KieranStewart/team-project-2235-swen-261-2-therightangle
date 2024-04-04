import { Component, Input, OnInit } from '@angular/core';
import { Account } from '../account';
import { AccountCasheService } from '../account-cashe.service';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  accountList: Account[] = [];
  @Input()
  account!: Account;

  constructor(public accountCasheService: AccountCasheService, public loginService: LoginService) { }

  ngOnInit(): void {
    this.getAccountList();
  }

  showDetails(account: Account): void {
    this.accountCasheService.selectedAccount = account;
  }

  getAccountList(): void {
    this.loginService.getAccounts().subscribe(accountList => this.accountList = accountList);
  }

  isAdmin(): boolean {
    if (this.loginService.userAccount != null)
    {return this.loginService.userAccount.isAdmin}
    return false;
  }
}
