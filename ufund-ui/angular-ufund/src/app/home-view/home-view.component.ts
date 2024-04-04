import { Component } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { Observable, take } from 'rxjs';
import { LoginService } from '../login.service';
import { Account } from '../account';

@Component({
  selector: 'app-home-view',
  templateUrl: './home-view.component.html',
  styleUrls: ['./home-view.component.css']
})

export class HomeViewComponent {
  searchResults: Need[] = []; //stores search results
  searchTerm: string = ''; //declares term searched 
  lastTermSearched: string = ''; // stores the last term that we actually searched the cupboard with

  constructor(
    private needService: NeedService,
    private loginService: LoginService) {}

    currentUser: Account = this.loginService.userAccount

  /**
   * Search for needs
   * @param thisSearchTerm Pass in the search term so that it does not change while searching.
   */
  search(thisSearchTerm: string): void {
    if(thisSearchTerm == null || thisSearchTerm == "") {
      this.clearSearch();
    } else {
      const that = this;
      this.needService.searchCupboard(thisSearchTerm).pipe(
        take(1) // unsubscribes after we get the search results
      ).subscribe({
        next(value) {
            if(value.length == 0) {
              that.clearSearch();
              that.lastTermSearched = thisSearchTerm;
            } else {
              that.searchResults = value;
            }
        },
      });
    }
  }

  clearSearch(): void {
    this.lastTermSearched = "";
    this.searchResults = [];
  }

  userIsAdmin(): boolean{
    return this.currentUser.isAdmin;
  }
}
