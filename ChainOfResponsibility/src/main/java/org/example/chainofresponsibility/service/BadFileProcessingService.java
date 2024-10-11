package org.example.chainofresponsibility.service;

import lombok.RequiredArgsConstructor;
import org.example.chainofresponsibility.handlers.DocFileHandler;
import org.example.chainofresponsibility.handlers.PdfFileHandler;
import org.example.chainofresponsibility.handlers.TextFileHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BadFileProcessingService {

    private final DocFileHandler docFileHandler;
    private final PdfFileHandler pdfFileHandler;
    private final TextFileHandler textFileHandler;

    public void processFile(String fileName) {
        if (fileName.endsWith(".doc")) {
            docFileHandler.handleFile(fileName);
        } else if (fileName.endsWith(".pdf")) {
            pdfFileHandler.handleFile(fileName);
        } else if(fileName.endsWith(".txt")) {
            textFileHandler.handleFile(fileName);
        } else throw new RuntimeException("File extension does not support!");
    }
}

