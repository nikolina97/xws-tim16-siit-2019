import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { UserRegistrationDTO } from '../models/user-registration-dto';

@Component({
  selector: 'app-registration-reviewer',
  templateUrl: './registration-reviewer.component.html',
  styleUrls: ['./registration-reviewer.component.css']
})
export class RegistrationReviewerComponent implements OnInit {
  formReg: FormGroup;
  expertises: Set<string> = new Set();
	constructor(
		private fb: FormBuilder,
		private router: Router,
		private authService: AuthService,
	) {
		this.formReg = this.fb.group({
			firstName: [null, Validators.required],
			lastName: [null, Validators.required],
			email: [null, [Validators.required, Validators.pattern("[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9}")]],
      password: [null, Validators.required],
			repeatedPassword: [null, Validators.required],
      universityName: [null, Validators.required],
      universityCity: [null, Validators.required],
      universityCountry: [null, Validators.required],
      expertise: [null]
		});
	}

	ngOnInit() {
	}

  register(){
    let user: UserRegistrationDTO={
		  firstName: this.formReg.value.firstName,
		  lastName: this.formReg.value.lastName,
		  email: this.formReg.value.email,
		  password: this.formReg.value.password,
      universityCity: this.formReg.value.universityCity,
      universityName: this.formReg.value.universityName,
      universityCountry: this.formReg.value.universityCountry,
      role: "ROLE_REVIEWER",
      expertises: Array.from(this.expertises.values())
  }
		console.log(user);
		const repeatedPassword = this.formReg.value.repeatedPassword;
		if(user.password !== repeatedPassword){
			alert("Passwords are not the same");
			return;
		}
		this.authService.register(user).subscribe(
			result => {
				alert("Registration successfull");
				this.router.navigate(['login'])
			},
			error => {
				console.log(error);
				alert(error.error);
			}
	  );
  }

  addExpertise(){
    this.expertises.add(this.formReg.value.expertise.toLowerCase());
    console.log(this.expertises);
  }

  removeExpertise(exp){
    this.expertises.delete(exp);
    console.log(this.expertises);
  }
}
