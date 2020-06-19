import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatButtonModule } from "@angular/material/button";
import {MatTableModule} from '@angular/material/table';
import {MatFormFieldModule, MatInputModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ScientificPaperRoutingModule } from './scientific-paper-routing.module';
import { PublishingComponent } from './publishing/publishing.component';
import { SearchPapersComponent } from './search-papers/search-papers.component';
import { PapersFromAuthorComponent } from './papers-from-author/papers-from-author.component';
import { ReviewerPapersComponent } from './reviewer-papers/reviewer-papers.component';


@NgModule({
  declarations: [PublishingComponent, SearchPapersComponent, PapersFromAuthorComponent, ReviewerPapersComponent],
  imports: [
    CommonModule,
    ScientificPaperRoutingModule,
    MatButtonModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class ScientificPaperModule { }
