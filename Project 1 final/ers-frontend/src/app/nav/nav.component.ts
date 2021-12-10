import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {NgbNavConfig} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss'],
  providers: [NgbNavConfig]
})
export class NavComponent implements OnInit {

  constructor(private router: Router,
    config: NgbNavConfig) { 
    config.destroyOnHide = false;
    config.roles = false;}

  ngOnInit(): void {
  }
  
  logOut() {
    console.log('Log out cleared');
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    this.router.navigate(['']);
  }
}
