import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {LoginRequest} from "../models/login-request.model";
import {SignupRequest} from "../models/signup-request.model";

@Injectable()
export class AuthenticationService {
  constructor(private http: HttpClient) {
  }

  login(user: LoginRequest): Observable<any> {
    return this.http.post<any>('http://localhost:8088/api/auth/sign-in', user);
  }

  register(user: SignupRequest): Observable<any> {
    return this.http.post('http://localhost:8088/api/auth/sign-up', user
    );
  }
}
