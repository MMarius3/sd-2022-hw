import {Component, Input} from "@angular/core";
import {Router} from "@angular/router";
import {DxDataGridComponent} from "devextreme-angular";
import { UserService } from "src/app/api/services/user.service";

@Component({
  selector: 'app-user-toolbar',
  templateUrl :'./toolbar.component.html',
  styleUrls : ['toolbar.component.css']
})
export class UserToolbarComponent {

  // @ts-ignore
  @Input() grid: DxDataGridComponent;

  buttonName: string = 'View books';

  urlPage: string = '/admin/books';
  constructor(private userService: UserService,
    private router: Router) {

  }

  public logout() {
    localStorage.setItem('user', '')
    this.router.navigate(['/login']);
  }

  public onAddUser(): void {
    this.router.navigate(['/add-user']);
  }

  public onDeleteUser(): void {
    this.userService.deleteUser(this.grid.selectedRowKeys[0].id);
    this.router.navigate(['/admin/users']);
  }

  public changePage(): void {
    this.router.navigate([this.urlPage]);
  }

  get areBooksSelected(): boolean {
    if(this.grid == null) {
      return false;
    }
    return this.grid.instance.getSelectedRowsData().length > 0;
  }
}
