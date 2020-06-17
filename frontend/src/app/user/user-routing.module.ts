import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistrationAuthorComponent } from './registration-author/registration-author.component';
import { RegistrationReviewerComponent } from './registration-reviewer/registration-reviewer.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'registration-author', component: RegistrationAuthorComponent },
  { path: 'registration-reviewer', component: RegistrationReviewerComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
