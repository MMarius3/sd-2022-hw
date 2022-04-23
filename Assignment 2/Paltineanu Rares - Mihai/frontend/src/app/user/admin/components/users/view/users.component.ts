import { Component, OnInit, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { DxDataGridComponent } from "devextreme-angular";
import { UserService } from "src/app/api/services/user.service";
import { UserGrid } from "src/app/models/user-grid.model";
import { User } from "src/app/models/user.model";


@Component({
    selector: 'app-users-view',
    templateUrl: 'users.component.html',
    styleUrls: ['users.component.css']
})
export class UsersViewComponent implements OnInit{
    @ViewChild(DxDataGridComponent)
    grid!: DxDataGridComponent;

    public usersGrid: UserGrid[] = [];
    public usersList: User[] = [];

    public bookButtonName: string = 'View books';

    public bookButtonUrl: string = 'admin/books';

    constructor(private router: Router,
        private userService: UserService) {}

    ngOnInit(): void {
        this.userService.getUsers()
            .subscribe(users =>  {
                this.usersList = users;
                this.updateUsersGrid();
            })
    }

    private updateUsersGrid() {
        this.usersList.forEach(user => {
            this.usersGrid.push(this.convertUser(user));
        })
    }

    private convertUser(user: User): UserGrid {
        let userGrid: UserGrid = new UserGrid(user);
        userGrid.roleNames = user.roles?.join('; ');
        return userGrid;
    }

    public openUserDetails(user: User): void {
        this.router.navigate(['/admin/users/' + user.id]);
    }
}