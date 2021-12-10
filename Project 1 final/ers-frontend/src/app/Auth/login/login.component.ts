import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { LoginResponse } from '../login.response';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginRequest = {
    email: String,
    password: String,
  };

  isLoading: boolean = false;
  isSuccess: string = '';
  errorRes: any;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }


  onSubmit(form: NgForm) {
    if (!form.valid) {
      return;
    }
    this.loginRequest.email = form.value.email;
    this.loginRequest.password = form.value.password;
    this.isLoading = true;
    this.authService.login(this.loginRequest).subscribe(
      (res: LoginResponse) => {
        this.isLoading = false;
        this.isSuccess = res.message;
        console.log("Login success");
        localStorage.setItem('token', res.token);
        localStorage.setItem('userId', res.userId);
        if("USER".toLowerCase() === res.role.toLowerCase()){
          this.router.navigate(['/userhome']);
        }else{
          this.router.navigate(['/adminhome']);
        }
        
      },
      (errorMessage: any) => {
        this.isLoading = false;
        this.errorRes = errorMessage;
      }
    );
    form.reset();
  }

  
}
