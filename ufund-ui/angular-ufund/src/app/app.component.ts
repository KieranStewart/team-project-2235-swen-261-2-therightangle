import { Component } from '@angular/core';
import { NeedCacheService } from './need-cache.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(public needCacheService: NeedCacheService) {}
  title = 'angular-ufund';

}
