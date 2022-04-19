import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {authHeader} from "../authentication/http";
import { Book } from "src/app/models/book.model";

@Injectable()
export class BooksService {
  constructor(private http: HttpClient) {}

  getBooks(): Observable<any> {
    const headers = authHeader()
    return this.http.get('http://localhost:8088/books/get-books', {headers})
  }

  addBook(book: Book): Observable<any> {
    const headers = authHeader();
    this.http.post<Book>('http://localhost:8088/books/add-book', book, {headers}).subscribe()
    return this.getBooks();
  }

  deleteBook(id: number): Observable<any> {
    const headers = authHeader();
    const url = `http://localhost:8088/books/delete-book/${id}`;
    this.http.delete<void>(url, {headers}).subscribe()
    return this.getBooks()
  }
}
