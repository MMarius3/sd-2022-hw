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
import {ToolbarComponent} from "./shared/toolbar/toolbar.component";
import { BooksViewComponent } from './user/admin/components/books-view/books.component';
import {DxButtonModule, DxDataGridModule} from "devextreme-angular";
import {BooksService} from "./api/services/books.service";
import {AddBookComponent} from "./user/admin/components/add-book/addBook.component";
import { UsersViewComponent } from './user/admin/components/users-view/users.component';
import { EmployeeBooksViewComponent } from './user/employee/components/books-view/books.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    ToolbarComponent,
    BooksViewComponent,
    AddBookComponent,
    UsersViewComponent,
    EmployeeBooksViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    RouterModule,
    DxDataGridModule
  ],
  providers: [
    AuthenticationService,
    BooksService,
    AuthenticationGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
