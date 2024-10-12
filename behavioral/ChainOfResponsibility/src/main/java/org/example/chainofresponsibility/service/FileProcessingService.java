package org.example.chainofresponsibility.service;

import lombok.RequiredArgsConstructor;
import org.example.chainofresponsibility.handlers.FileHandler;
import org.springframework.stereotype.Service;

import static org.example.chainofresponsibility.utils.MessagesUtils.PROCESSING_COMPLETE;

@Service
@RequiredArgsConstructor
public class FileProcessingService {

    private final FileHandler fileHandlerChain;

    public String processFile(String fileName) {
        fileHandlerChain.handleFile(fileName);
        return PROCESSING_COMPLETE + fileName;
    }
}

