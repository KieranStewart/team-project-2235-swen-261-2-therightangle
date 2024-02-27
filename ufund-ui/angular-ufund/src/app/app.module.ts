import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { NeedComponent } from './need/need.component';
import { CupboardComponent } from './cupboard/cupboard.component';

@NgModule({
  declarations: [
    AppComponent,
    NeedComponent,
    CupboardComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
