import { Component } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { Observable, take } from 'rxjs';

@Component({
  selector: 'app-home-view',
  templateUrl: './home-view.component.html',
  styleUrls: ['./home-view.component.css']
})

export class HomeViewComponent {
  searchResults: Need[] = []; //stores search results
  searchTerm: string = ''; //declares term searched 

  constructor(private needService: NeedService) {}

  /**
   * Search for needs
   * @param thisSearchTerm Pass in the search term so that it does not change while searching.
   */
  search(thisSearchTerm: string): void {
    console.log(thisSearchTerm);
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
            } else {
              that.searchResults = value;
            }
        },
      });
    }
  }

  clearSearch(): void {
    this.searchResults = [];
  }
}
