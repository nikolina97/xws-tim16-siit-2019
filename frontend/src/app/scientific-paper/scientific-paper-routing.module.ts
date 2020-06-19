import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PublishingComponent } from './publishing/publishing.component';
import { SearchPapersComponent } from './search-papers/search-papers.component';
import { PapersFromAuthorComponent } from './papers-from-author/papers-from-author.component';
import { SubmittedPapersComponent } from './submitted-papers/submitted-papers.component';

const routes: Routes = [
  { path: '', redirectTo: 'paper/search', pathMatch: 'full' },
  { path: 'paper/new', component: PublishingComponent },
  { path: 'paper/search', component: SearchPapersComponent},
  { path: 'paper/byAuthor', component: PapersFromAuthorComponent},
  { path: 'paper/submittedPapers', component: SubmittedPapersComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ScientificPaperRoutingModule { }
