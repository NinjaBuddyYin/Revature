import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, throwError } from 'rxjs';
import { RegisterResponse } from '../register-response';
import { catchError, tap } from 'rxjs/operators';
import { User } from '../user.model';
import { LoginResponse } from './login.response';




@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseUrl = environment.baseUrl;
  user = new BehaviorSubject<User>({} as any);

  constructor(private http: HttpClient) { }

  login(loginRequest: any) {
    let url = `http://localhost:8000/user/login`;
    let headers = new HttpHeaders();
    headers.append('Accept', 'application/json');
    headers.append('Content-Type', 'application/json');
    return this.http
      .post<LoginResponse>(url, loginRequest, {
        headers: headers,
      })
      .pipe(
        catchError((errorRes) => {
          let errorMessage = 'Unknown error occured';
          if (!errorRes.error || errorRes.error.error) {
            return throwError(errorMessage);
          } else {
            errorMessage = errorRes.error.message;
          }

          return throwError(errorMessage);
        }),
        tap((resData) => {
          let user = new User(resData.userName, resData.email, resData.token);
          this.user.next(user);
        })
      );
  }

  isLoggedIn() {
    return !!localStorage.getItem('token');
  }

  register(userRequest: any) {
    let url = `http://localhost:8000/user/registration`;
    return this.http
      .post<RegisterResponse>(url, userRequest, {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Accept: 'application/json',
        }),
      })
      .pipe(
        catchError((errorRes) => {
          let errorMessage = 'Unknown error occured';
          if (!errorRes.error || !errorRes.error.error) {
            return throwError(errorMessage);
          } else {
            errorMessage = errorRes.error.message;
          }

          return throwError(errorMessage);
        })
      );
  }
}


