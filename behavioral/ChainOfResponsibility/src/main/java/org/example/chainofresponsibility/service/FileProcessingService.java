package org.example.chainofresponsibility.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.example.chainofresponsibility.handlers.FileHandler;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTDrawing;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import static org.example.chainofresponsibility.utils.MessagesUtils.PROCESSING_COMPLETE;

@Service
@RequiredArgsConstructor
public class FileProcessingService {

    private final FileHandler fileHandlerChain;

    public String processFile(String fileName) {
        // Call file processing through chain of responsibility
        fileHandlerChain.handleFile(fileName);
        return PROCESSING_COMPLETE + fileName;
    }
}

