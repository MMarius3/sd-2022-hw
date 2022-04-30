import { Component, Input } from "@angular/core";
import { Router } from "@angular/router";
import { DxDataGridComponent } from "devextreme-angular";
import { BookService } from "src/app/api/services/book.service";
import { Book } from "src/app/models/book.model";

@Component({
    selector: 'app-employee-toolbar',
    templateUrl: 'employee-toolbar.component.html',
    styleUrls: ['employee-toolbar.component.css']
})
export class EmployeeToolbarComponent{

    //@ts-ignore
    @Input() grid: DxDataGridComponent;
    public quantity: number = 0;
    public filter: string = '';
    constructor(private router: Router,
        private bookService: BookService) {}

    public logout(): void {
        localStorage.setItem('user', '');
        this.router.navigate(['/login']);
    }

    public onSellBook(): void {
        if(!this.isBookSelected) {
            alert("Please select a book");
            return;
        }

        const bookId: number = this.grid.selectedRowKeys[0].id;
        this.bookService.getBookById(bookId).subscribe(book => {
            if(book == undefined) {
                alert("Book with id " + bookId + " not found");
                return;
            }
            if(book.quantity! < this.quantity) {
                alert("Book's quantity must be greater or equal with the chosen quantity");
                return;
            }
            book.quantity! -= this.quantity;
            this.bookService.updateBook(bookId, book).subscribe(books => this.grid.dataSource = books);
        });

        
    }

    public onFilter(): void {
        this.bookService.filterBooks(this.filter).subscribe(books => this.grid.dataSource = books);
    }

    get isBookSelected(): boolean {
        if(this.grid == null) {
          return false;
        }
        return this.grid.instance.getSelectedRowsData().length > 0;
    }
}