import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppComponent } from './app.component';

import { LoginComponent } from './login/login.component';
import { AccountCreationComponent } from './account-creation/account-creation.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes =[
  {path: '', component: AppComponent },
  {path: 'Login', component: LoginComponent },
  {path: 'AccountCreation', component: AccountCreationComponent },
]

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes),
    CommonModule
  ]
})
export class AppRoutingModule {}
