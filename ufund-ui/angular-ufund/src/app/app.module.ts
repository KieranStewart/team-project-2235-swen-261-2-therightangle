import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms'

import { AppComponent } from './app.component';
import { NeedComponent } from './need/need.component';
import { CupboardComponent } from './cupboard/cupboard.component';
import { FundingBasketComponent } from './funding-basket/funding-basket.component';
import { NeedDetailComponent } from './need-detail/need-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    NeedComponent,
    CupboardComponent,
    FundingBasketComponent,
    NeedDetailComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
