package org.example.chainofresponsibility.model;

public enum FileType {
    TXT(".txt"),
    DOC(".doc"),
    PDF(".pdf");

    private final String extension;

    FileType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static FileType getByExtension(String fileName) {
        for (FileType fileType : FileType.values()) {
            if (fileName.endsWith(fileType.getExtension())) {
                return fileType;
            }
        }
        return null; // Если расширение не поддерживается
    }
}

