package com.assignment2.book_store.service;

import com.assignment2.book_store.helper.report.ReportType;
import org.springframework.http.HttpHeaders;

import java.io.IOException;


public interface ReportService {

    byte[] export(String content) ;
    ReportType getType();
    HttpHeaders getHeaders();

}
