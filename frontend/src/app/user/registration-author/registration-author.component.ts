import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { UserRegistrationDTO } from '../models/user-registration-dto';

@Component({
  selector: 'app-registration-author',
  templateUrl: './registration-author.component.html',
  styleUrls: ['./registration-author.component.css']
})
export class RegistrationAuthorComponent implements OnInit {
  formReg: FormGroup;

	constructor(
		private fb: FormBuilder,
		private router: Router,
		private authService: AuthService,
	) {
		this.formReg = this.fb.group({
			firstName: [null, Validators.required],
			lastName: [null, Validators.required],
			email: [null, [Validators.required,Validators.pattern("[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9}")]],
      password: [null, Validators.required],
			repeatedPassword: [null, Validators.required],
      universityName: [null, Validators.required],
      universityCity: [null, Validators.required],
      universityCountry: [null, Validators.required],
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
      role: "ROLE_AUTHOR",
      expertises: null
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
}
