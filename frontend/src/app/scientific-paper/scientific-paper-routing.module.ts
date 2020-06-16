import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PublishingComponent } from './publishing/publishing.component';


const routes: Routes = [
  { path: 'paper/new', component: PublishingComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ScientificPaperRoutingModule { }
