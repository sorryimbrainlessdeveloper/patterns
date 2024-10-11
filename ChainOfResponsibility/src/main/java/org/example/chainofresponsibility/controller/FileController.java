package org.example.chainofresponsibility.controller;

import lombok.RequiredArgsConstructor;
import org.example.chainofresponsibility.service.FileProcessingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileProcessingService fileProcessingService;

    @GetMapping("/open-file")
    public String openFile(@RequestParam String fileName) {
        return fileProcessingService.processFile(fileName);
    }
}

