import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { async, Observable } from "rxjs";
import { USERS_URL } from "src/app/http/http-urls.component";
import { User } from "src/app/models/user.model";
import { authHeader } from "../authentication/http";

@Injectable()
export class UserService {
    constructor(private http: HttpClient) {}

    public getUsers(): Observable<any> {
        const headers = authHeader();
        const url: string = USERS_URL + '/get-users';
        return this.http.get(url, {headers});
    }

    public deleteUser(id: number): Observable<any> {
        const headers = authHeader();
        const url: string = USERS_URL + `/delete-user/${id}`;
        this.http.delete(url, {headers}).subscribe();
        return this.getUsers();
    }

    public addUser(user: User): Observable<any> {
        const headers = authHeader()
        const url: string = USERS_URL + '/add-user';
        this.http.post(url, user, {headers}).subscribe();
        return this.getUsers();
    }

    public getUserById(id: number): Observable<any> {
        const headers = authHeader()
        const url: string = USERS_URL + `/get-users/${id}`;
        return this.http.get(url, {headers});
    }

    public updateUser(id: number, user: User) {
        
    }
}