import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PublishingComponent } from './publishing/publishing.component';
import { SearchPapersComponent } from './search-papers/search-papers.component';
import { PapersFromAuthorComponent } from './papers-from-author/papers-from-author.component';
import { WriteRevisionComponent } from './write-revision/write-revision.component';
import { ReviewerPapersComponent } from './reviewer-papers/reviewer-papers.component';
import { SubmittedPapersComponent } from './submitted-papers/submitted-papers.component';
import { OnRevisePapersComponent } from './on-revise-papers/on-revise-papers.component';

const routes: Routes = [
  { path: '', redirectTo: 'paper/search', pathMatch: 'full' },
  { path: 'paper/new', component: PublishingComponent },
  { path: 'paper/search', component: SearchPapersComponent },
  { path: 'paper/byAuthor', component: PapersFromAuthorComponent },
  { path: 'paper/write-revision/:id', component: WriteRevisionComponent },
  { path: 'paper/search', component: SearchPapersComponent },
  { path: 'paper/byAuthor', component: PapersFromAuthorComponent },
  { path: 'paper/reviewer', component: ReviewerPapersComponent },
  { path: 'paper/submittedPapers', component: SubmittedPapersComponent },
  { path: 'paper/onRevisePapers', component: OnRevisePapersComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ScientificPaperRoutingModule { }
