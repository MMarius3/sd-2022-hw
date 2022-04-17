import {Component, Input} from "@angular/core";
import {Router} from "@angular/router";
import {DxDataGridComponent} from "devextreme-angular";
import {BooksService} from "../../api/services/books.service";

@Component({
  selector: 'app-toolbar',
  templateUrl :'./toolbar.component.html',
  styleUrls : ['toolbar.component.css']
})
export class ToolbarComponent {

  // @ts-ignore
  @Input() grid: DxDataGridComponent;
  constructor(private bookService: BooksService,
    private router: Router) {

  }

  public logout() {
    localStorage.setItem('user', '')
    this.router.navigate(['/login']);
  }

  public onAddBook(): void {
    this.router.navigate(['/add-book'])
  }

  public onDeleteBook(): void {
    this.bookService.deleteBook(this.grid.selectedRowKeys[0].id)
  }
}
