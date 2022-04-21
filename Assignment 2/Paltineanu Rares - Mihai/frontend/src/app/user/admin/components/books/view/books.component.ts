import {Component, OnDestroy, OnInit, ViewChild} from "@angular/core";
import {Router} from "@angular/router";
import {BookService} from "../../../../../api/services/book.service";
import {DxDataGridComponent} from "devextreme-angular";
import { Book } from "src/app/models/book.model";

@Component({
  selector: 'app-main-view',
  templateUrl : 'books.component.html',
  styleUrls: ['books.component.css']
})
export class BooksViewComponent implements OnInit, OnDestroy{
  @ViewChild(DxDataGridComponent)
  grid!: DxDataGridComponent;

  public books: Book[] = [];

  constructor(private router: Router,
              private booksService: BookService) {
  }

  ngOnInit(): void {
    this.booksService.getBooks()
      .subscribe(books => {
        this.books = books
      });
  }
  
  public openBookDetails(book: Book): void {
    this.router.navigate(['/admin/books/' + book.id]);
  }

  ngOnDestroy(): void {
  }
}
