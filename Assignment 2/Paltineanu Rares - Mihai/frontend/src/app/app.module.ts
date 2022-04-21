import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {RegisterComponent} from "./authentication/components/register/register.component";
import {LoginComponent} from "./authentication/components/login/login.component";
import {FormsModule} from "@angular/forms";
import {AuthenticationService} from "./api/services/authentication.service";
import {HttpClientModule} from "@angular/common/http";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {RouterModule} from "@angular/router";
import {AuthenticationGuard} from "./authentication/guards/authentication.guard";
import { BooksViewComponent } from './user/admin/components/books/view/books.component';
import {DxButtonModule, DxDataGridModule} from "devextreme-angular";
import {BookService} from "./api/services/book.service";
import {AddBookComponent} from "./user/admin/components/books/add/add-book.component";
import { UsersViewComponent } from './user/admin/components/users/view/users.component';
import { EmployeeBooksViewComponent } from './user/employee/components/books-view/books.component';
import { EditBookComponent } from './user/admin/components/books/edit/edit-book.component';
import { UserService } from './api/services/user.service';
import { BookToolbarComponent } from './user/admin/components/books/toolbar/toolbar.component';
import { UserToolbarComponent } from './user/admin/components/users/toolbar/toolbar.component';
import { AddUserComponent } from './user/admin/components/users/add/add-user.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    BookToolbarComponent,
    BooksViewComponent,
    AddBookComponent,
    UsersViewComponent,
    EmployeeBooksViewComponent,
    EditBookComponent,
    UserToolbarComponent,
    AddUserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    RouterModule,
    DxDataGridModule,
    DxButtonModule
  ],
  providers: [
    AuthenticationService,
    BookService,
    AuthenticationGuard,
    UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
