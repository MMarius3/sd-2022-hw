import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { REPORT_URL } from "src/app/http/http-urls.component";
import { authHeader } from "../authentication/http";

@Injectable()
export class ReportService {
    constructor(private http: HttpClient) {}

    public export(type: string): void {
        const headers = authHeader();
        const url: string = REPORT_URL + '/' + type;
        console.log(url);
        this.http.get(url, {headers}).subscribe(data => console.log("data " + data));
    }
}