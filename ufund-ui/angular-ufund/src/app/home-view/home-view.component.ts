import { Component } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home-view',
  templateUrl: './home-view.component.html',
  styleUrls: ['./home-view.component.css']
})

export class HomeViewComponent {
  searchResults$: Observable<Need[]> | null = null; //stores search results
  searchTerm: string = ''; //declares term searched 

  constructor(private needService: NeedService) {}

  search(searchTerm: string): void {
    this.searchResults$ = this.needService.searchCupboard(searchTerm);
  }
}
