import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CheckoutViewComponent } from './checkout-view/checkout-view.component';
import { HomeViewComponent } from './home-view/home-view.component';
import { ConfirmationViewComponent } from './confirmation-view/confirmation-view.component';
import { AppComponent } from './app.component';

import { LoginComponent } from './login/login.component';
import { AccountCreationComponent } from './account-creation/account-creation.component';
import { TagCreationComponent } from './tag-creation/tag-creation.component';
import { UserComponent } from './user/user.component';
import { NeedEditComponent } from './need-edit/need-edit.component';

const routes: Routes = [
  {
    path: 'checkout',
    component: CheckoutViewComponent
  },
  {
    path: 'confirmation/:state',
    component: ConfirmationViewComponent
  },
  {
    path: '',
    component: HomeViewComponent
  },
  { path: '', component: AppComponent },
  { path: 'Login', component: LoginComponent },
  { path: 'AccountCreation', component: AccountCreationComponent },
  { path: 'CreateTags', component: TagCreationComponent },
  { path: 'ManageUsers', component: UserComponent },

];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
  static getRoutes(): Routes {
    return routes;
  }
}
