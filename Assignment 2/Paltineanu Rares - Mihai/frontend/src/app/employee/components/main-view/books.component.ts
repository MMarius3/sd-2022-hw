import {Component, OnInit} from "@angular/core";
import {Book} from "../../models/book.model";
import {Router} from "@angular/router";
import {BooksService} from "../../../api/services/books.service";

@Component({
  selector: 'app-main-view',
  templateUrl : 'books.component.html',
  styleUrls: ['books.component.css']
})
export class BooksComponent implements OnInit{
  public books: Book[] = [];

  constructor(private router: Router,
              private booksService: BooksService) {
  }

  ngOnInit(): void {
    this.booksService.getBooks()
      .subscribe(books => {
        this.books = books
      });
  }
}
