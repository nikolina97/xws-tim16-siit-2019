import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ScientificPaperModule } from './scientific-paper/scientific-paper.module';
import { SharedModule } from './shared/shared.module';
import { CoverLetterModule } from './cover-letter/cover-letter.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ScientificPaperModule,
    SharedModule,
    CoverLetterModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
