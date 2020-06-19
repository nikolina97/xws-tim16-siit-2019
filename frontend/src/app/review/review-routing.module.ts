import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AssignReviewerComponent } from './assign-reviewer/assign-reviewer.component';
import { AuthorPaperReviewsComponent } from './author-paper-reviews/author-paper-reviews.component';

const routes: Routes = [
  { path: 'review/assign/:paperId', component: AssignReviewerComponent },
  { path: 'review/author-review/:paperId', component: AuthorPaperReviewsComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReviewRoutingModule { }
