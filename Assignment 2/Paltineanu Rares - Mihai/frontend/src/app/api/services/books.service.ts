import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Book} from "../../employee/models/book.model";
import {HttpClient} from "@angular/common/http";
import {authHeader} from "../authentication/http";

@Injectable()
export class BooksService {
  constructor(private http: HttpClient) {}

  getBooks(): Observable<any> {
    const headers = authHeader()
    return this.http.get('http://localhost:8088/books/get-books', {headers})
  }

  addBook(book: Book): Observable<Book> {
    const headers = authHeader();
    return this.http.post<Book>('http://localhost:8088/books/add-book', {book})
  }

  deleteBook(id: number): Observable<any> {
    const headers = authHeader();
    const url = `http://localhost:8088/books/delete-book/${id}`;
    this.http.delete<void>(url, {headers}).subscribe()
    return this.getBooks()
  }
}
