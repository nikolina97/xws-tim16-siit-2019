import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AssignReviewerComponent } from './assign-reviewer/assign-reviewer.component';
import { WriteReviewComponent } from './write-review/write-review.component';

const routes: Routes = [
  { path: 'review/assign/:paperId', component: AssignReviewerComponent },
  { path: 'review/write-review/:paperId', component: WriteReviewComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReviewRoutingModule { }
