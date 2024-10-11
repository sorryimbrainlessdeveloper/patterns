package org.example.chainofresponsibility.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DocFileHandlerTest {

    private static final String DOC_EXTENSION = "example.doc";
    private static final String NOT_DOC_EXTENSION = "example.txt";

    private DocFileHandler handler;

    @BeforeEach
    void setUp() {
        handler = new DocFileHandler();
    }

    @Test
    void canHandleFile_docExtension() {
        assertTrue(handler.canHandleFile(DOC_EXTENSION));
    }

    @Test
    void canHandleFile_notDocExtension() {
        assertFalse(handler.canHandleFile(NOT_DOC_EXTENSION));
    }
}