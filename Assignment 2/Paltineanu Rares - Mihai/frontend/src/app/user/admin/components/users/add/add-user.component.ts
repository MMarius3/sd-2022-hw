import {Component, OnInit} from "@angular/core";
import { Router } from "@angular/router";
import { UserService } from "src/app/api/services/user.service";
import { UserAdd } from "src/app/models/user-add.model";

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
    //this.userService.addUser(this.form);
    this.router.navigate(['/admin/users']);
  }

  public onCancel(): void {
    this.router.navigate(['/admin/users']);
  }
}
