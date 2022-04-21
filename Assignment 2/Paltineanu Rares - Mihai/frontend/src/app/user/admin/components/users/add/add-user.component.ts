import {Component, OnInit} from "@angular/core";
import {Book} from "../../../../../models/book.model";
import {BookService} from "../../../../../api/services/book.service";
import { Router } from "@angular/router";
import { User } from "src/app/models/user.model";

@Component({
  selector: 'app-add-user',
  templateUrl: 'add-user.component.html',
  styleUrls: ['add-user.component.css']
})
export class AddUserComponent implements OnInit{
  public form: User = new User();
  public addUserFailed: boolean = false;
  public errors: string[] = [];
  public errorMessage = '';

  constructor(private bookService: BookService,
    private router: Router) {
  }

  ngOnInit(): void {

  }

  public addUser(): void {
    this.bookService.addBook(this.form);
    this.router.navigate(['/admin/books']);
  }

  public onCancel(): void {
    this.router.navigate(['/admin/books']);
  }
}
