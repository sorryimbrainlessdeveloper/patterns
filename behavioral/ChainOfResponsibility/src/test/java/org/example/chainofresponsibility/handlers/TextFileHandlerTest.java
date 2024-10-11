package org.example.chainofresponsibility.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextFileHandlerTest {

    private static final String TXT_EXTENSION = "example.txt";
    private static final String NOT_TXT_EXTENSION = "example.doc";

    private TextFileHandler handler;

    @BeforeEach
    void setUp() {
        handler = new TextFileHandler();
    }

    @Test
    void canHandleFile_txtExtension() {
        assertTrue(handler.canHandleFile(TXT_EXTENSION));
    }

    @Test
    void canHandleFile_notTxtExtension() {
        assertFalse(handler.canHandleFile(NOT_TXT_EXTENSION));
    }
}