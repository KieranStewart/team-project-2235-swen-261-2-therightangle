import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms'

import { AppComponent } from './app.component';
import { NeedComponent } from './need/need.component';
import { CupboardComponent } from './cupboard/cupboard.component';
import { LoginComponent } from './login/login.component';
import { AccountCreationComponent } from './account-creation/account-creation.component';
import { AppRoutingModule } from './app-routing.module';
import { FundingBasketComponent } from './funding-basket/funding-basket.component';
import { NeedDetailComponent } from './need-detail/need-detail.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { RouterModule, Routes } from '@angular/router'; // Same as below comment
import { CheckoutViewComponent } from './checkout-view/checkout-view.component';
import { HomeViewComponent } from './home-view/home-view.component';
import { ConfirmationViewComponent } from './confirmation-view/confirmation-view.component';
import { TransactionHistoryDisplayComponent } from './transaction-history-display/transaction-history-display.component';
import { TagCreationComponent } from './tag-creation/tag-creation.component';
import { UserComponent } from './user/user.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { NeedEditComponent } from './need-edit/need-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    NeedComponent,
    CupboardComponent,
    LoginComponent,
    AccountCreationComponent,
    FundingBasketComponent,
    NeedDetailComponent,
    CheckoutComponent,
    CheckoutViewComponent,
    HomeViewComponent,
    ConfirmationViewComponent,
    TransactionHistoryDisplayComponent,
    TagCreationComponent,
    UserComponent,
    UserDetailComponent,
    NeedEditComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule,
    RouterModule.forRoot(AppRoutingModule.getRoutes())
  ],
  providers: [CupboardComponent, TransactionHistoryDisplayComponent, HomeViewComponent, NeedComponent, NeedDetailComponent],
  bootstrap: [AppComponent]
  
})
export class AppModule { }
