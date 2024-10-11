package org.example.chainofresponsibility.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PdfFileHandlerTest {

    private static final String PDF_EXTENSION = "example.pdf";
    private static final String NOT_PDF_EXTENSION = "example.doc";

    private PdfFileHandler handler;

    @BeforeEach
    void setUp() {
        handler = new PdfFileHandler();
    }

    @Test
    void canHandleFile_pdfExtension() {
        assertTrue(handler.canHandleFile(PDF_EXTENSION));
    }

    @Test
    void canHandleFile_notPdfExtension() {
        assertFalse(handler.canHandleFile(NOT_PDF_EXTENSION));
    }
}