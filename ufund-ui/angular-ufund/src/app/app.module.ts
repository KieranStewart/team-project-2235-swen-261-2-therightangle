import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

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
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
