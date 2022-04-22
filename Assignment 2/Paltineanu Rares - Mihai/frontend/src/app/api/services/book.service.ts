import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {authHeader} from "../authentication/http";
import { Book } from "src/app/models/book.model";
import { BOOKS_URL } from "src/app/http/http-urls.component";

@Injectable()
export class BookService {
  constructor(private http: HttpClient) {}

  getBooks(): Observable<any> {
    const headers = authHeader()
    const url: string = BOOKS_URL + '/get-books';
    return this.http.get(url, {headers})
  }

  getBookById(id: number): Observable<any> {
    const headers = authHeader();
    const url: string = BOOKS_URL + `/get-book/${id}`;
    return this.http.get(url, {headers});
  }

  addBook(book: Book): Observable<any> {
    const headers = authHeader();
    const url: string = BOOKS_URL + '/add-book';
    this.http.post<Book>(url, book, {headers}).subscribe()
    return this.getBooks();
  }

  deleteBook(id: number): Observable<any> {
    const headers = authHeader();
    const url = BOOKS_URL + `/delete-book/${id}`;
    this.http.delete<void>(url, {headers}).subscribe()
    return this.getBooks()
  }

  updateBook(id: number, book: Book): Observable<Book> {
    const headers = authHeader();
    const url = BOOKS_URL + `/update-book/${id}`;
    this.http.put(url, book, {headers})
      .subscribe(updatedBook => {
        if(updatedBook != null) {
          console.log('Book with id ' + id + ' updated successfully');
        } else {
          console.log('Failed to update book with id ' + id);
        }
      });
    return this.getBooks();
  }
}
