package com.assignment2.book_store.helper.report.pdf.enums;

public enum PdfBuilderType {
    ITEXT("ITEXT"),
    PDF_BOX("PDF_BOX");

    private final String name;

    private PdfBuilderType(String name) {
        this.name = name;
    }

}
