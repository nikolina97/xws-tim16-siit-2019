import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { UserRegistrationDTO } from '../models/user-registration-dto';

@Injectable({
	providedIn: 'root'
})
export class AuthService {
	private headers = new HttpHeaders({ 'Content-Type': 'application/json',
										'Access-Control-Allow-Origin': '*',
										'Access-Control-Allow-Credentials': 'true'});
	constructor(
		private http: HttpClient
	) { }

	login(auth: any): Observable<any> {
		let loginUrl =  "http://localhost:8081/auth/login";
		return this.http.post(loginUrl, {username: auth.username, password: auth.password}, {headers: this.headers, responseType: 'text'});
	}

	logout(): Observable<any> {
		let logoutUrl = "http://localhost:8081/auth/logout";
		return this.http.get(logoutUrl, {headers: this.headers, responseType: 'text'});
	}

	isLoggedIn(): boolean {
		if (localStorage.getItem('user')) {
				return true;
		}
		return false;
  }
  getRole(): string {
		const token = localStorage.getItem('user');
		const jwt: JwtHelperService = new JwtHelperService();
		const info = jwt.decodeToken(token);
		return info.role[0].authority;
  }

  isReviewer(): boolean{
	const token = localStorage.getItem('user');
	if(token!=null){
		if (this.getRole()=="ROLE_REVIEWER")
			return true;
		}
	else
		return false;
 }

 isEditor(): boolean{
	const token = localStorage.getItem('user');
	if(token!=null){
		if (this.getRole()=="ROLE_EDITOR")
			return true;
		}
	else
		return false;
 }


  register(dto: UserRegistrationDTO): Observable<any> {
		let registerUrl =  "http://localhost:8081/auth/registration";
		return this.http.post(registerUrl, dto, {headers: this.headers, responseType: 'text'});
	}
}