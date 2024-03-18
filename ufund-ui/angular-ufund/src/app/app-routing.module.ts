import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CheckoutViewComponent } from './checkout-view/checkout-view.component';
import { HomeViewComponent } from './home-view/home-view.component';

const routes: Routes = [
  {
    path: 'checkout',
    component: CheckoutViewComponent
  },
  {
    path: '',
    component: HomeViewComponent
  }
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
