import {Component, Input} from "@angular/core";
import {Router} from "@angular/router";
import {DxDataGridComponent} from "devextreme-angular";
import { BookService } from "src/app/api/services/book.service";

@Component({
  selector: 'app-book-toolbar',
  templateUrl :'./toolbar.component.html',
  styleUrls : ['toolbar.component.css']
})
export class BookToolbarComponent {

  // @ts-ignore
  @Input() grid: DxDataGridComponent;

  buttonName: string = 'View users';

  urlPage: string = '/admin/users';

  constructor(private bookService: BookService,
    private router: Router) {
  }

  public logout() {
    localStorage.setItem('user', '')
    this.router.navigate(['/login']);
  }

  public onAddBook(): void {
    this.router.navigate(['/add-book']);
  }

  public onDeleteBook(): void {
    this.bookService.deleteBook(this.grid.selectedRowKeys[0].id);
    this.router.navigate(['/admin/books']);
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
