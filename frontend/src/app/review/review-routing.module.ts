import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AssignReviewerComponent } from './assign-reviewer/assign-reviewer.component';

const routes: Routes = [{ path: 'review/assign/:paperId', component: AssignReviewerComponent },];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReviewRoutingModule { }
