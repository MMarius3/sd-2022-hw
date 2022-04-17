import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {LoginRequest} from "../../authentication/models/login-request.model";
import {SignupRequest} from "../../authentication/models/signup-request.model";
import {authHeader} from "../authentication/http";

@Injectable()
export class AuthenticationService {
  constructor(private http: HttpClient) {
  }

  login(user: LoginRequest): Observable<any> {
    return this.http.post('http://localhost:8088/api/auth/sign-in', user);
  }

  register(user: SignupRequest): Observable<any> {
    return this.http.post('http://localhost:8088/api/auth/sign-up', user
    );
  }

  getAllUsers(): Observable<any> {
    const headers = authHeader()
    return this.http.get('http://localhost:8088/api/user', {headers});
  }

  isAuthenticated(): boolean {
    return localStorage.getItem('user') !== '' ;
  }
}
