import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../services/authentication.service";
import {TokenService} from "../../services/token.service";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {LoginRequest} from "../../models/login-request.model";
import {Observable} from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, CanActivate {
  form: LoginRequest = new LoginRequest();
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  constructor(private authenticationService: AuthenticationService,
              private tokenService: TokenService,
              private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    if (this.tokenService.getToken()) {
      console.log('aaa')
      this.isLoggedIn = true;
      this.roles = this.tokenService.getUser().roles;
      this.router.navigate(['/home']);
      return true;
    }
    return false;
  }

  ngOnInit(): void {
  }

  attemptLogin(): void {
    this.authenticationService.login(this.form).subscribe(
      data => {
        console.log(data)
        this.tokenService.saveToken(data.token);
        this.tokenService.saveUser(data);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenService.getUser().roles;
        this.reloadPage();
        console.log('success')
      },
      err => {
        console.log('error')
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  attemptRegister(): void {
    this.router.navigate(['/register']);
  }

  reloadPage(): void {
    window.location.reload();
  }
}
