import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatButtonModule } from "@angular/material/button";
import {MatTableModule} from '@angular/material/table';
import {MatFormFieldModule, MatInputModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatDialogModule} from '@angular/material/dialog';

import { ReviewRoutingModule } from './review-routing.module';
import { WriteReviewComponent } from './write-review/write-review.component';
import { AssignReviewerComponent, ExpertiseDialog } from './assign-reviewer/assign-reviewer.component';

@NgModule({
  declarations: [WriteReviewComponent, AssignReviewerComponent, ExpertiseDialog],
  entryComponents: [ExpertiseDialog],
  imports: [
    CommonModule,
    ReviewRoutingModule,
    MatButtonModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule
  ]
})
export class ReviewModule { }
