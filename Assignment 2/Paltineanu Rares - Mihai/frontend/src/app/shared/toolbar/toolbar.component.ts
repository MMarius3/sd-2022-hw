import {Component} from "@angular/core";
import {Router} from "@angular/router";

@Component({
  selector: 'app-toolbar',
  templateUrl :'./toolbar.component.html',
  styleUrls : ['toolbar.component.css']
})
export class ToolbarComponent {

  constructor(private router: Router) {

  }

  public logout() {
    localStorage.setItem('user', '')
    this.router.navigate(['/login']);
  }

  public onAddBook(): void {
    this.router.navigate(['/add-book'])
  }

  public onDeleteBook(): void {

  }
}
