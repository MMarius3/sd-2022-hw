import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegisterComponent} from "./authentication/components/register/register.component";
import {LoginComponent} from "./authentication/components/login/login.component";
import {AuthenticationGuard} from "./authentication/guards/authentication.guard";
import { BooksViewComponent } from './user/admin/components/books-view/books.component';
import {AddBookComponent} from "./user/admin/components/add-book/addBook.component";
import { EmployeeBooksViewComponent } from './user/employee/components/books-view/books.component';
const routes: Routes = [
  { path: '', component: LoginComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'admin', component: BooksViewComponent, canActivate: [AuthenticationGuard]},
  { path: 'add-book', component: AddBookComponent, canActivate: [AuthenticationGuard]},
  { path: 'employee', component: EmployeeBooksViewComponent, canActivate: [AuthenticationGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
