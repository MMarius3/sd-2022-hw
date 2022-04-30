import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { BookService } from "src/app/api/services/book.service";
import { Book } from "src/app/models/book.model";

@Component({
    selector: 'app-edit-book',
    templateUrl: 'edit-book.component.html',
    styleUrls: ['edit-book.component.css']
})
export class EditBookComponent implements OnInit {

    public book: Book;
    public updateBookFailed: boolean = false;
    public errors: string[] = [];
    public errorMessage = '';

    constructor(private router: Router,
        private booksService: BookService) {}

    ngOnInit(): void {
        let url = this.router.url;
        let bookIdPosition = url.lastIndexOf('/');
        const bookId = +url.substring(bookIdPosition + 1)
        this.booksService.getBookById(bookId).subscribe(book => this.book = book);
    }

    public onCancel(): void {
        this.router.navigate(['/admin/books']);
    }

    public updateBook(): void {
        if(this.book != undefined) {
            this.booksService.updateBook(this.book.id!, this.book);
        }
        this.router.navigate(['/admin/books']);
    }
}