import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { LoginComponent } from './login/login.component';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RegistrationAuthorComponent } from './registration-author/registration-author.component';
import { RegistrationReviewerComponent } from './registration-reviewer/registration-reviewer.component';
import { MatButtonModule } from '@angular/material/button';


@NgModule({
  declarations: [LoginComponent, RegistrationAuthorComponent, RegistrationReviewerComponent],
  imports: [
    CommonModule,
    UserRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    MatButtonModule,
  ]
})
export class UserModule { }
