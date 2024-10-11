package org.example.chainofresponsibility.handlers;

import org.example.chainofresponsibility.model.FileType;
import org.springframework.stereotype.Component;

import static org.example.chainofresponsibility.utils.MessagesUtils.OPEN_FILE;

@Component
public class DocFileHandler extends FileHandler {

    @Override
    protected boolean canHandleFile(String fileName) {
        return FileType.DOC.equals(FileType.getByExtension(fileName));
    }

    @Override
    protected void openFile(String fileName) {
        System.out.printf(OPEN_FILE, fileName);
    }
}

