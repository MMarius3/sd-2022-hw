import {Component, OnInit} from "@angular/core";
import {Book} from "../../models/book.model";
import {BooksService} from "../../../api/services/books.service";

@Component({
  selector: 'app-add-book',
  templateUrl: 'addBook.component.html',
  styleUrls: ['addBook.component.css']
})
export class AddBookComponent implements OnInit{
  public form: Book = new Book();
  public addBookFailed: boolean = false;
  public errors: string[] = [];
  errorMessage = '';

  constructor(private bookService: BooksService) {
  }
  ngOnInit(): void {

  }

  public addBook(): void {
    this.bookService.addBook(this.form);
  }
}
