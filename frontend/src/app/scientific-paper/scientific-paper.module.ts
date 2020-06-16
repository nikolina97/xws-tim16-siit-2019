import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatButtonModule } from "@angular/material/button";

import { ScientificPaperRoutingModule } from './scientific-paper-routing.module';
import { PublishingComponent } from './publishing/publishing.component';


@NgModule({
  declarations: [PublishingComponent],
  imports: [
    CommonModule,
    ScientificPaperRoutingModule,
    MatButtonModule
  ]
})
export class ScientificPaperModule { }
