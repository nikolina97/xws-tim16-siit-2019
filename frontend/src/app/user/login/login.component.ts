import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthService } from '../services/auth.service';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
	form: FormGroup;

	constructor(
		private fb: FormBuilder,
		private router: Router,
		private authService: AuthService,
	) {
		this.form = this.fb.group({
			username: [null, [Validators.required, Validators.pattern("[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9}")]],
			password: [null, Validators.required]
		});
	}

	ngOnInit() {
	}

	submit() {
		const auth: any = {};
		const jwt: JwtHelperService = new JwtHelperService();
		auth.username = this.form.value.username;
    	auth.password = this.form.value.password;
    	console.log(auth);
		this.authService.login(auth).subscribe(
			result => {
				alert('Login successfull!');
				// alert('Login successfull!')
				localStorage.setItem('user', result);
				//console.log(result);
				//console.log(jwt.decodeToken(result));
				let info = jwt.decodeToken(result)
				console.log(info);
				console.log(this.authService.getRole())
				this.router.navigate(["/"]);
			},
			error => {
				console.log(error);
        		alert(error.error);
			}
		);
	}
}