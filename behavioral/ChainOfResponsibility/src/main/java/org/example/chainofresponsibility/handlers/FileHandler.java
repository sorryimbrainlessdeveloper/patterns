package org.example.chainofresponsibility.handlers;

import lombok.Setter;

import static org.example.chainofresponsibility.utils.MessagesUtils.NO_HANDLERS_FOUND;

@Setter
public abstract class FileHandler {

    protected FileHandler nextHandler;

    public void handleFile(String fileName) {
        if (canHandleFile(fileName)) {
            openFile(fileName);
        } else if (nextHandler != null) {
            nextHandler.handleFile(fileName);
        } else {
            throw new RuntimeException(NO_HANDLERS_FOUND + fileName);
        }
    }

    protected abstract boolean canHandleFile(String fileName);

    protected abstract void openFile(String fileName);
}

