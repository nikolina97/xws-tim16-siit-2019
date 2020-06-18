import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PublishingComponent } from './publishing/publishing.component';
import { SearchPapersComponent } from './search-papers/search-papers.component';

const routes: Routes = [
  { path: '', redirectTo: 'paper/search', pathMatch: 'full' },
  { path: 'paper/new', component: PublishingComponent },
  { path: 'paper/search', component: SearchPapersComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ScientificPaperRoutingModule { }
