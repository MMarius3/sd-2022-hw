import {Component, Input} from "@angular/core";
import {Router} from "@angular/router";
import {DxDataGridComponent} from "devextreme-angular";
import { BookService } from "src/app/api/services/book.service";
import { ReportService } from "src/app/api/services/report.service";

enum ReportType{
  CSV = 'CSV',
  PDF = 'PDF'
}

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
    private reportService: ReportService,
    private router: Router) {
  }

  public logout() {
    localStorage.setItem('user', '')
    this.router.navigate(['/login']);
  }

  public onAddBook(): void {
    this.router.navigate(['/admin/add-book']);
  }

  public onDeleteBook(): void {
    this.bookService.deleteBook(this.grid.selectedRowKeys[0].id);
    this.router.navigate(['/admin/books']);
  }

  public changePage(): void {
    this.router.navigate([this.urlPage]);
  }

  public generateCsvReport(): void {
    this.reportService.export(ReportType.CSV);
  }

  public generatePdfReport(): void {
    this.reportService.export(ReportType.PDF);
  }

  get isBookSelected(): boolean {
    if(this.grid == null) {
      return false;
    }
    return this.grid.instance.getSelectedRowsData().length > 0;
  }
}
