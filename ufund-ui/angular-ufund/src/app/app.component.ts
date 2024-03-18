import { Component } from '@angular/core';
import { NeedCacheService } from './need-cache.service';
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(public needCacheService: NeedCacheService) {}
  title = 'angular-ufund';

}
