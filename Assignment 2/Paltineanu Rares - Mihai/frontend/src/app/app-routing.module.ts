import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegisterComponent} from "./authentication/components/register/register.component";
import {LoginComponent} from "./authentication/components/login/login.component";
import {AuthenticationGuard} from "./authentication/guards/authentication.guard";
import {BooksComponent} from "./employee/components/main-view/books.component";
import {AddBookComponent} from "./employee/components/add-book/addBook.component";
const routes: Routes = [
  { path: '', component: LoginComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'employee', component: BooksComponent, canActivate: [AuthenticationGuard]},
  { path: 'add-book', component: AddBookComponent, canActivate: [AuthenticationGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
