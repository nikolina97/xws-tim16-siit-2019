import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistrationAuthorComponent } from './registration-author/registration-author.component';
import { RegistrationReviewerComponent } from './registration-reviewer/registration-reviewer.component';
import { LoginGuard } from '../guards/login.service';


const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [LoginGuard]},
  { path: 'registration-author', component: RegistrationAuthorComponent, canActivate: [LoginGuard] },
  { path: 'registration-reviewer', component: RegistrationReviewerComponent, canActivate: [LoginGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
