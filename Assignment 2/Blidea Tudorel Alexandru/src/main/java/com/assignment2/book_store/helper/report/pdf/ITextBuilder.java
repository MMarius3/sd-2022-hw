package com.assignment2.book_store.helper.report.pdf;

import com.assignment2.book_store.helper.report.pdf.enums.PdfTextPosition;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Slf4j
public class ITextBuilder implements PdfBuilder {

    private static final Float TITLE_DEFAULT_FONT_SIZE = 20.0f;
    private static final String DEFAULT_FONT = FontFactory.TIMES_ROMAN;
    private static final Map<PdfTextPosition, Integer> TEXT_POSITIONING_MAP = Map.of(
            PdfTextPosition.CENTER, Element.ALIGN_CENTER,
            PdfTextPosition.LEFT, Element.ALIGN_LEFT,
            PdfTextPosition.RIGHT, Element.ALIGN_RIGHT);
    private static final Float DEFAULT_FONT_SIZE = 12.0f;
    private final Document document;
    private final ByteArrayOutputStream outputStream;
    private PdfTextPosition currentTextPosition;
    private Font currentFont;

    public ITextBuilder() {
        currentTextPosition = PdfTextPosition.LEFT;
        currentFont = FontFactory.getFont(DEFAULT_FONT, DEFAULT_FONT_SIZE, Font.NORMAL);
        outputStream = new ByteArrayOutputStream();
        document = new Document();
        try {
            PdfWriter.getInstance(document, outputStream);
        } catch (DocumentException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        document.open();
    }

    @Override
    public PdfBuilder addParagraph(String pdfParagraph) {
        Paragraph paragraph = new Paragraph(pdfParagraph, currentFont);
        paragraph.setAlignment(TEXT_POSITIONING_MAP.get(currentTextPosition));
        addParagraph(paragraph);
        return this;
    }

    @Override
    public PdfBuilder addTitle(String pdfTitle) {
        Font font = FontFactory.getFont(DEFAULT_FONT, TITLE_DEFAULT_FONT_SIZE, Font.BOLD, BaseColor.BLACK);
        Paragraph title = new Paragraph(pdfTitle, font);
        title.setAlignment(TEXT_POSITIONING_MAP.get(PdfTextPosition.CENTER));
        addParagraph(title);
        return this;
    }

    @Override
    public PdfBuilder setCurrentFontSize(Float fontSize) {
        currentFont = FontFactory.getFont(DEFAULT_FONT, fontSize, TEXT_POSITIONING_MAP.get(currentTextPosition));
        return this;
    }

    @Override
    public PdfBuilder setCurrentTextPosition(PdfTextPosition textPosition) {
        this.currentTextPosition = textPosition;
        return this;
    }

    @Override
    public byte[] buildToByteArray() throws IOException {
        return convertDocumentToByteArray();
    }

    private byte[] convertDocumentToByteArray() throws IOException {
        document.close();
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return inputStream.readAllBytes();
    }

    @Override
    public void close() throws IOException {
        document.close();
    }

    private void addParagraph(Paragraph paragraph) {
        try {
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
