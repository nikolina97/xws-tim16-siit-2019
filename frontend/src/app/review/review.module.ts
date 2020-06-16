import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReviewRoutingModule } from './review-routing.module';
import { WriteReviewComponent } from './write-review/write-review.component';


@NgModule({
  declarations: [WriteReviewComponent],
  imports: [
    CommonModule,
    ReviewRoutingModule
  ]
})
export class ReviewModule { }
