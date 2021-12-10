import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { RegisterResponse } from '../../register-response';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  errorRes:String = '';
  successRes:String = '';

  userRequest = {
    
    userFirstName :String,
    userLastName:String,
    userEmail:String,
    ersPassword:String,
    ersUserName:String,
    confirmPassword:String,
    userRole:String
  }

  constructor(private router:Router,
    private authService:AuthService) { }

  ngOnInit(): void {
  }

  onSubmit(form:NgForm){
    this.userRequest.userFirstName = form.value.userFirstName;
    this.userRequest.userLastName = form.value.userLastName;
    this.userRequest.userEmail = form.value.userEmail;
    this.userRequest.ersPassword = form.value.ersPassword;
    this.userRequest.ersUserName = form.value.ersUserName;
    this.userRequest.confirmPassword = form.value.confPassword;
    this.userRequest.userRole = form.value.userRole;
    
    this.authService.register(this.userRequest)
    .subscribe((data:RegisterResponse) => {
      this.successRes = data.message;
      setTimeout(() =>{ this.router.navigate(['']); }, 3000);
      
    },
    (errorMessage: any) => {
      this.errorRes = errorMessage;
    });
    form.reset();
  }

}
