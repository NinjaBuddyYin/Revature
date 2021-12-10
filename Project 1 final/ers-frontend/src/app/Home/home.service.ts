import { HttpClient, HttpEvent, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HomeResponse } from '../home_response';
import { RegisterResponse } from '../register-response';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(private http:HttpClient) { }

  reimSubmit(reimbRequest: any) {
    let url = `http://localhost:8000/user/add`;
    return this.http
      .post<RegisterResponse>(url, reimbRequest, {
        headers: new HttpHeaders({
          'Authorization': 'Bearer ' + localStorage.getItem('token')
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

  getUserReimb():Observable<HomeResponse[]>{
    let userId: any = localStorage.getItem('userId');
    let url = 'http://localhost:8000/user/reimbursement/request'
    return this.http.get<HomeResponse[]>(url, {
      headers : new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }),
      params : new HttpParams().set('userId', userId )
    });
  }

  getAllUserReim():Observable<any[]>{
    let url = 'http://localhost:8000/admin/all/reimbursement'
    return this.http.get<any[]>(url, {
      headers : new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }),
    });
  }


  approveReim(id:any):Observable<any[]>{
    let url = 'http://localhost:8000/admin/approve'
    return this.http.get<any[]>(url, {
      headers : new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }),
      params : new HttpParams().set('userId', id )
    });
  }

  denyReim(id:any):Observable<any[]>{
    let url = 'http://localhost:8000/admin/deny';
    return this.http.get<any[]>(url, {
      headers : new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }),
      params : new HttpParams().set('userId', id )
    });
  }

  filterByStatus(status:string):Observable<any[]>{
    let url = 'http://localhost:8000/admin/filter/status';
    return this.http.get<any[]>(url, {
      headers : new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }),
      params : new HttpParams().set('status', status )
    });
  }

  filterByStatusUser(status:string):Observable<any[]>{
    let url = 'http://localhost:8000/user/filter/status';
    return this.http.get<any[]>(url, {
      headers : new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }),
      params : new HttpParams().set('status', status )
    });
  }

  fileDownloadByAdmin(fileName:string):Observable<HttpEvent<Blob>>{
    let url = 'http://localhost:8000/admin/file/download';
    return this.http.get(url,{
      reportProgress:true,
      observe:'events',
      responseType: 'blob',
      headers : new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }),
      params : new HttpParams().set('fileName', fileName ),
      
    });
  }
}
