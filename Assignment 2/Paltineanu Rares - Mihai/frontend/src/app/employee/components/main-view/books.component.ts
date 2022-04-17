import {Component, OnInit, ViewChild} from "@angular/core";
import {Book} from "../../models/book.model";
import {Router} from "@angular/router";
import {BooksService} from "../../../api/services/books.service";
import {DxDataGridComponent} from "devextreme-angular";

@Component({
  selector: 'app-main-view',
  templateUrl : 'books.component.html',
  styleUrls: ['books.component.css']
})
export class BooksComponent implements OnInit{
  @ViewChild(DxDataGridComponent) grid: DxDataGridComponent;

  public books: Book[] = [];

  constructor(private router: Router,
              private booksService: BooksService) {
  }

  ngOnInit(): void {
    console.log(this.grid)
    this.booksService.getBooks()
      .subscribe(books => {
        this.books = books
      });
  }
}
