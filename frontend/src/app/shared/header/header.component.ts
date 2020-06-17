import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/user/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit() {
  }

  logout(){
    localStorage.removeItem('user');
    this.auth.logout().subscribe(
      result => {
        this.router.navigate(['login']);
      },
      error => {
        alert(error.error);
      }
    );
  }
}
