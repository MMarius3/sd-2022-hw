import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { UserService } from "src/app/api/services/user.service";
import { UserGrid } from "src/app/models/user-grid.model";
import { User } from "src/app/models/user.model";

@Component({
    selector: 'app-edit-user',
    templateUrl: 'edit-user.component.html',
    styleUrls: ['edit-user.component.css']
})
export class EditUserComponent implements OnInit{

    //@ts-ignore
    public user: UserGrid;
    public updateUserFailed: boolean = false;
    public errors: string[] = [];
    public errorMessage = '';

    constructor(private userService: UserService, 
        private router: Router) {}

    ngOnInit(): void {
        let url = this.router.url;
        let bookIdPosition = url.lastIndexOf('/');
        const userId = +url.substring(bookIdPosition + 1)
        
        this.userService.getUserById(userId).subscribe(user => {
            this.user = new UserGrid(user);
        });
    }

    public onCancel(): void {
        this.router.navigate(['/admin/users']);
    }

    public updateUser(): void {
        if(this.user != undefined) {
            let editUser: User = new User(this.user);
            this.userService.updateUser(this.user.id!, editUser);
        }
        this.router.navigate(['/admin/users']);
    }

}