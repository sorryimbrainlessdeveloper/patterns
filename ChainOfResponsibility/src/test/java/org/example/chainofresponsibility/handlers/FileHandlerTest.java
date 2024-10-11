package org.example.chainofresponsibility.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    private static final String TXT_EXTENSION = "example.txt";
    private static final String DOC_EXTENSION = "example.doc";
    private static final String PDF_EXTENSION = "example.pdf";
    private static final String UNKNOWN_EXTENSION = "example.zxc";

    private TextFileHandler textFileHandler;
    private DocFileHandler docFileHandler;
    private PdfFileHandler pdfFileHandler;

    @BeforeEach
    void setUp() {
        textFileHandler = new TextFileHandler();
        docFileHandler = new DocFileHandler();
        pdfFileHandler = new PdfFileHandler();

        //set chain of handlers
        textFileHandler.setNextHandler(docFileHandler);
        docFileHandler.setNextHandler(pdfFileHandler);
    }

    @Test
    void chain_handleTextFile() {
        textFileHandler.handleFile(TXT_EXTENSION);
        assertTrue(textFileHandler.canHandleFile(TXT_EXTENSION));
    }

    @Test
    void chain_handleDocFile() {
        docFileHandler.handleFile(DOC_EXTENSION);
        assertTrue(docFileHandler.canHandleFile(DOC_EXTENSION));
    }

    @Test
    void chain_handlePdfFile() {
        pdfFileHandler.handleFile(PDF_EXTENSION);
        assertTrue(pdfFileHandler.canHandleFile(PDF_EXTENSION));
    }

    @Test
    void chain_noHandlerForUnknownFileExtension() {
        assertThrows(RuntimeException.class,
                ()-> textFileHandler.handleFile(UNKNOWN_EXTENSION));
    }
}