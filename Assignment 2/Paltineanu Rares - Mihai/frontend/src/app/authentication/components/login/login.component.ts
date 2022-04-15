import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../services/authentication.service";
import {TokenService} from "../../services/token.service";
import {Router} from "@angular/router";
import {LoginRequest} from "../../models/login-request.model";
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: LoginRequest = new LoginRequest();
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  constructor(private authenticationService: AuthenticationService,
              private tokenService: TokenService,
              private router: Router) { }
  ngOnInit(): void {
    if (this.tokenService.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenService.getUser().roles;
    }
  }
  attemptLogin(): void {
    this.authenticationService.login(this.form).subscribe(
      data => {
        this.tokenService.saveToken(data.accessToken);
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
    // this.authenticationService.register(this.user).subscribe(
    //   data => {
    //     this.tokenService.saveToken(data.accessToken);
    //     this.tokenService.saveUser(data);
    //     this.isLoginFailed = false;
    //     this.isLoggedIn = true;
    //     this.roles = this.tokenService.getUser().roles;
    //     this.reloadPage();
    //     console.log('success')
    //   },
    //   err => {
    //     console.log('error')
    //     this.errorMessage = err.error.message;
    //     this.isLoginFailed = true;
    //   }
    // );
    this.router.navigate(['/register']);
  }

  reloadPage(): void {
    window.location.reload();
  }
}
