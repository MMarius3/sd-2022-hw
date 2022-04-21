import {Component, OnInit} from "@angular/core";
import {Book} from "../../../../../models/book.model";
import {BookService} from "../../../../../api/services/book.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-add-book',
  templateUrl: 'add-book.component.html',
  styleUrls: ['add-book.component.css']
})
export class AddBookComponent implements OnInit{
  public form: Book = new Book();
  public addBookFailed: boolean = false;
  public errors: string[] = [];
  public errorMessage = '';

  constructor(private bookService: BookService,
    private router: Router) {
  }

  ngOnInit(): void {

  }

  public addBook(): void {
    this.bookService.addBook(this.form);
    this.router.navigate(['/admin/books']);
  }

  public onCancel(): void {
    this.router.navigate(['/admin/books']);
  }
}
