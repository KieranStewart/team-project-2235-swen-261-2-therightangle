import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms'

import { AppComponent } from './app.component';
import { NeedComponent } from './need/need.component';
import { CupboardComponent } from './cupboard/cupboard.component';
import { FundingBasketComponent } from './funding-basket/funding-basket.component';
import { NeedDetailComponent } from './need-detail/need-detail.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { RouterModule, Routes } from '@angular/router'; // Same as below comment
import { AppRoutingModule } from './app-routing.module';
@NgModule({
  declarations: [
    AppComponent,
    NeedComponent,
    CupboardComponent,
    FundingBasketComponent,
    NeedDetailComponent,
    CheckoutComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule,
    RouterModule.forRoot(AppRoutingModule.getRoutes())
  ],
  providers: [],
  bootstrap: [AppComponent]
  
})
export class AppModule { }
