import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { AuthService } from '../user/services/auth.service';

@Injectable({
	providedIn: 'root'
})
export class LoginGuard implements CanActivate {

	constructor(
		public auth: AuthService,
		public router: Router
	) { }

	canActivate(): boolean {
		if (this.auth.isLoggedIn()) {
			this.router.navigate(['/page-not-found']);
			return false;
		}
		return true;
	}
}
