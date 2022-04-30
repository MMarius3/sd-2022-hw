package com.assignment2.book_store.helper.report.pdf;

import com.assignment2.book_store.helper.report.pdf.enums.PdfTextPosition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class PdfBoxBuilder implements PdfBuilder {

    private static PDType1Font DEFAULT_FONT = PDType1Font.TIMES_ROMAN;
    private static Float TITLE_DEFAULT_FONT_SIZE = 20.0f;
    private static Integer START_Y_PADDING = 5;
    private static Integer PDF_WIDTH = 600;
    private static Integer PDF_HEIGHT = 800;
    private static Integer BOTTOM_MARGIN = 50;
    private static PdfTextPosition DEFAULT_TEXT_POSITION = PdfTextPosition.LEFT;

    private PDDocument document;
    private PDPage page;
    private PDPageContentStream pdPageContentStream;
    private Float fontSize = 12.0f;
    private Integer marginX = 50;
    private Integer yPageCursor;
    private PdfTextPosition currentTextPosition;

    public PdfBoxBuilder() throws IOException {
        document = new PDDocument();
        resetPdf();
    }

    @Override
    public PdfBuilder addParagraph(String paragraph) throws IOException {
        List<String> lines = fitTheText(paragraph);
        int charsPerLine = computeCharsPerLine();
        lines.forEach(line -> {
            try {
                writeLine(line, charsPerLine);
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new RuntimeException("Error while writing the line " + line);
            }
            yPageCursor += (int) (1.5 * fontSize);
        });
        return this;
    }

    @Override
    public PdfBuilder addTitle(String title) throws IOException {
        Float previousFontSize = fontSize;
        yPageCursor += (int) (1.5 * TITLE_DEFAULT_FONT_SIZE);
        PdfTextPosition previousTextPosition = this.currentTextPosition;
        return this.setCurrentTextPosition(PdfTextPosition.CENTER)
                .setCurrentFontSize(TITLE_DEFAULT_FONT_SIZE)
                .addParagraph(title)
                .setCurrentFontSize(previousFontSize)
                .setCurrentTextPosition(previousTextPosition);
    }

    @Override
    public PdfBuilder setCurrentFontSize(Float fontSize) {
        this.fontSize = fontSize;
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

    @Override
    public void close() throws IOException {
        document.close();
    }

    private byte[] convertDocumentToByteArray() throws IOException {
        pdPageContentStream.close();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return inputStream.readAllBytes();
    }

    private int computeCharsPerLine() {
        int result = (int) (2.4 * (600 - 1.6 * marginX) / fontSize);
        log.debug("result: " + result + ", fontSize: " + fontSize);
        return result;
    }

    private List<String> fitTheText(String text) {
        int charsPerLine = computeCharsPerLine();
        return List.of(WordUtils.wrap(text, charsPerLine).split("\\r?\\n"));
    }

    private void writeLine(String line, Integer charsPerLine) throws IOException {
        verifyPageEnding();
        pdPageContentStream.beginText();
        pdPageContentStream.setFont(DEFAULT_FONT, fontSize);
        positionText(line, charsPerLine);
        pdPageContentStream.endText();
    }

    private void alignCenter(String line, Integer charsPerLine) throws IOException {
        int remainingCharacters = Math.abs(charsPerLine - line.length());
        int paddingLeft = remainingCharacters / 2;
        int paddingRight = remainingCharacters - paddingLeft;
        String result = StringUtils.repeat(" ", paddingLeft) + line + StringUtils.repeat(" ", paddingRight);
        pdPageContentStream.newLineAtOffset(marginX, PDF_HEIGHT - yPageCursor);
        pdPageContentStream.showText(result);
    }

    private void alignRight(String line, Integer charsPerLine) throws IOException {
        String leftAlignmentFormat = String.format("%%%ds", charsPerLine);
        String result = String.format(leftAlignmentFormat, line);
        pdPageContentStream.newLineAtOffset(marginX, PDF_HEIGHT - yPageCursor);
        pdPageContentStream.showText(result);
    }

    private void alignLeft(String line, Integer charsPerLine) throws IOException {
        pdPageContentStream.newLineAtOffset(marginX, PDF_HEIGHT - yPageCursor);
        pdPageContentStream.showText(line);
    }

    private void positionText(String line, Integer charsPerLine) throws IOException {
        switch (currentTextPosition) {
            case RIGHT -> {
                alignRight(line, charsPerLine);
            }
            case CENTER -> {
                alignCenter(line, charsPerLine);
            }
            default -> {
                alignLeft(line, charsPerLine);
            }
        }
    }

    private void verifyPageEnding() throws IOException {
        if (yPageCursor + fontSize > PDF_HEIGHT - BOTTOM_MARGIN) {
            pdPageContentStream.close();
            resetPdf();
        }
    }

    private void resetPdf() throws IOException {
        page = new PDPage();
        document.addPage(page);
        pdPageContentStream = new PDPageContentStream(document, page);
        yPageCursor = START_Y_PADDING + (int) (1.5 * fontSize);
        this.currentTextPosition = DEFAULT_TEXT_POSITION;
    }

}
