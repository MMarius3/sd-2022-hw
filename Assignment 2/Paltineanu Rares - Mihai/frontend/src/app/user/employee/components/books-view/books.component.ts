import { Component, OnInit, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { DxDataGridComponent } from "devextreme-angular";
import { BookService } from "src/app/api/services/book.service";
import { Book } from "src/app/models/book.model";


@Component({
    selector: 'app-employee-books',
    templateUrl: 'books.component.html',
    styleUrls: ['books.component.css']
})
export class EmployeeBooksViewComponent implements OnInit{
    //@ts-ignore
    @ViewChild(DxDataGridComponent) grid: DxDataGridComponent;
    public books: Book[] = [];

    constructor(private router: Router,
        private bookService: BookService) {}

    public ngOnInit(): void {
        this.bookService.getBooks().subscribe(books => this.books = books);
    }
}