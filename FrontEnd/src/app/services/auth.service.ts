import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';  // Add this import

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8081/api';

  constructor(private http: HttpClient) { }

  signup(signupData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, signupData);
  }

  login(loginData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, loginData);
  }

  forgotPassword(email: string): Observable<any> {
    console.log('Sending forgot password request:', { email });
    return this.http.post(`${this.apiUrl}/forgot-password`, { email })
      .pipe(
        tap((response: any) => {
          console.log('Forgot password response:', response);
        })
      );
  }

  resetPassword(email: string, newPassword: string): Observable<any> {
    console.log('Sending reset password request:', { email }); // Add debug log
    return this.http.post(`${this.apiUrl}/reset-password`, {
      email: email,
      newPassword: newPassword
    }).pipe(
      tap((response: any) => {
        console.log('Reset password response:', response);
      })
    );
  }

  verifyEmail(token: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/verify?token=${token}`);
  }
}