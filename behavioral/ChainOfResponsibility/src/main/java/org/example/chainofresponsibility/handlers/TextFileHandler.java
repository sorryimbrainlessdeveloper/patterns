package org.example.chainofresponsibility.handlers;

import org.example.chainofresponsibility.model.FileType;
import org.springframework.stereotype.Component;

import static org.example.chainofresponsibility.utils.MessagesUtils.OPEN_FILE;

@Component
public class TextFileHandler extends FileHandler {

    @Override
    protected boolean canHandleFile(String fileName) {
        return FileType.TXT.equals(FileType.getByExtension(fileName));
    }

    @Override
    protected void openFile(String fileName) {
        System.out.printf(OPEN_FILE, fileName);
    }
}

