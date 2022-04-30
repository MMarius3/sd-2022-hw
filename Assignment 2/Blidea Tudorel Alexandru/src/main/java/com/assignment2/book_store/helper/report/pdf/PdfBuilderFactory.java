package com.assignment2.book_store.helper.report.pdf;

import com.assignment2.book_store.helper.report.pdf.enums.PdfBuilderType;

import java.io.IOException;

public class PdfBuilderFactory {

    private PdfBuilderFactory() {

    }

    public static PdfBuilder getPdfBuilder(PdfBuilderType pdfBuilderType) throws IOException {
        switch (pdfBuilderType) {
            case PDF_BOX: {
                return new PdfBoxBuilder();
            }
            default: {
                return new ITextBuilder();
            }
        }
    }

}
