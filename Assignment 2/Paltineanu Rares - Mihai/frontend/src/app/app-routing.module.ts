import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegisterComponent} from "./authentication/components/register/register.component";
import {LoginComponent} from "./authentication/components/login/login.component";
import {AuthenticationGuard} from "./authentication/guards/authentication.guard";
import { BooksViewComponent } from './user/admin/components/books/view/books.component';
import {AddBookComponent} from "./user/admin/components/books/add/add-book.component";
import { EmployeeBooksViewComponent } from './user/employee/components/books-view/books.component';
import { UsersViewComponent } from './user/admin/components/users/view/users.component';
import { EditBookComponent } from './user/admin/components/books/edit/edit-book.component';
import { AddUserComponent } from './user/admin/components/users/add/add-user.component';
import { EditUserComponent } from './user/admin/components/users/edit/edit-user.component';

const routes: Routes = [
  { path: '', component: LoginComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'admin/books', component: BooksViewComponent, canActivate: [AuthenticationGuard]},
  { path: 'admin/books/:id', component: EditBookComponent, canActivate: [AuthenticationGuard]},
  { path: 'admin/add-book', component: AddBookComponent, canActivate: [AuthenticationGuard]},
  { path: 'employee', component: EmployeeBooksViewComponent, canActivate: [AuthenticationGuard]},
  { path: 'admin/users', component: UsersViewComponent, canActivate: [AuthenticationGuard]},
  { path: 'admin/add-user', component: AddUserComponent, canActivate: [AuthenticationGuard]},
  { path: 'admin/users/:id', component: EditUserComponent, canActivate:[AuthenticationGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
