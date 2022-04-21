import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {LoginRequest} from "../../authentication/models/login-request.model";
import {SignupRequest} from "../../authentication/models/signup-request.model";
import {authHeader} from "../authentication/http";
import { API_URL, AUTH_URL, BASE_URL } from "src/app/http/http-urls.component";

@Injectable()
export class AuthenticationService {
  constructor(private http: HttpClient) {
  }

  login(user: LoginRequest): Observable<any> {
    return this.http.post(AUTH_URL + '/sign-in', user);
  }

  register(user: SignupRequest): Observable<any> {
    return this.http.post(AUTH_URL + '/sign-up', user
    );
  }

  getAllUsers(): Observable<any> {
    const headers = authHeader()
    return this.http.get(API_URL + '/user', {headers});
  }

  isAuthenticated(): boolean {
    return localStorage.getItem('user') !== '' ;
  }

  isAdmin(): boolean {
    let user = JSON.parse(localStorage.getItem('user')!);
    if(user.roles.some((role:string) => role === 'ADMIN')) {
      return true;
    }
    return false;
  }
}
