import {Component, OnInit} from "@angular/core";
import { Router } from "@angular/router";
import { UserService } from "src/app/api/services/user.service";
import { SignupRequest } from "src/app/authentication/models/signup-request.model";
import { UserAdd } from "src/app/models/user-add.model";
import { User } from "src/app/models/user.model";

@Component({
  selector: 'app-add-user',
  templateUrl: 'add-user.component.html',
  styleUrls: ['add-user.component.css']
})
export class AddUserComponent implements OnInit{
  public form: UserAdd = new UserAdd();
  public addUserFailed: boolean = false;
  public errors: string[] = [];
  public errorMessage = '';

  constructor(private userService: UserService,
    private router: Router) {
  }

  ngOnInit(): void {
  }

  public addUser(): void {
    this.form.roles?.push('ADMIN');
    let addedUser: SignupRequest = this.toSignUpRequest();
    this.userService.addUser(addedUser);
    this.router.navigate(['/admin/users']);
  }

  private toSignUpRequest(): SignupRequest {
    let signupRequest: SignupRequest = new SignupRequest();
    signupRequest.username = this.form.name ?? '';
    signupRequest.email = this.form.email ?? '';
    signupRequest.password = this.form.password ?? '';
    signupRequest.roles = undefined;
    
    return signupRequest;
  }
  public onCancel(): void {
    this.router.navigate(['/admin/users']);
  }
}
