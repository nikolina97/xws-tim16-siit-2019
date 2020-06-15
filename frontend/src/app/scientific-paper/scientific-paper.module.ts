import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ScientificPaperRoutingModule } from './scientific-paper-routing.module';
import { PublicationComponent } from './publication/publication.component';


@NgModule({
  declarations: [PublicationComponent],
  imports: [
    CommonModule,
    ScientificPaperRoutingModule
  ]
})
export class ScientificPaperModule { }
