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
import {BooksComponent} from "./employee/components/main-view/books.component";
import {DxButtonModule, DxDataGridModule} from "devextreme-angular";
import {BooksService} from "./api/services/books.service";
import {AddBookComponent} from "./employee/components/add-book/addBook.component";

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    ToolbarComponent,
    BooksComponent,
    AddBookComponent
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
