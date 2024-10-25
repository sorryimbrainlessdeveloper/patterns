# Behavioral pattern `Chain of Responsibility` in Spring Boot

# ENG

## Description

The **Chain of Responsibility** is a behavioral design pattern that allows requests to be passed along a chain of handlers. Each handler can either process the request or pass it on to the next handler in the chain.

### Main Idea:
The request is passed from one handler to another until a handler is found that can handle the request. This provides flexibility in request processing, as new handlers can easily be added to the chain.

### Benefits:
- **Flexibility**: Easily add or remove handlers without changing the existing logic.
- **Modularity**: Each handler is responsible only for its own task.
- **Reusability**: Handlers can be reused in other chains or applications.
- **Maintainability**: The code is easy to maintain and extend.

### Service without Chain of Responsibility:
```java
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
        } else throw new RuntimeException("File extension is not supported!");
    }
}
```
### Service with Chain of Responsibility:
```java
@Service
@RequiredArgsConstructor
public class FileProcessingService {

    private final FileHandler fileHandlerChain;

    public String processFile(String fileName) {
        fileHandlerChain.handleFile(fileName);
        return PROCESSING_COMPLETE + fileName;
    }
}
```
- **Notice how much cleaner the code has become.**
---

## Implementation in Spring Boot

### 1.  Abstract Class `FileHandler`

This abstract class defines the method for file handling and stores a reference to the next handler in the chain:
```java
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
```

- **setNextHandler():** Sets the next handler in the chain.
- **handleFile():** The main processing method. If the current handler cannot process the request, it passes it further down the chain.
- **canHandleFile() и openFile():**  Abstract methods that will be implemented in each specific handler.

### 2. Concrete Handlers
Each handler is responsible for processing a specific file type:

- **Text file handler `TextFileHandler`**
```java
@Component
public class TextFileHandler extends FileHandler {

    @Override
    protected boolean canHandleFile(String fileName) {
        return FileType.TXT.equals(FileType.getByExtension(fileName));
    }

    @Override
    protected void openFile(String fileName) {
        System.out.println("Opening text file: " + fileName);
    }
}
```

- **Word file handler `DocFileHandler`**
```java
@Component
public class DocFileHandler extends FileHandler {

    @Override
    protected boolean canHandleFile(String fileName) {
        return FileType.DOC.equals(FileType.getByExtension(fileName));
    }

    @Override
    protected void openFile(String fileName) {
        System.out.println("Opening Word document: " + fileName);
    }
}
```

- **PDF file handler `PdfFileHandler`**
```java
@Component
public class PdfFileHandler extends FileHandler {

    @Override
    protected boolean canHandleFile(String fileName) {
        return FileType.PDF.equals(FileType.getByExtension(fileName));
    }

    @Override
    protected void openFile(String fileName) {
        System.out.println("Opening PDF file: " + fileName);
    }
}
```

### 3. Configuring the Chain of Handlers
In Spring Boot, the configuration of the chain can be done via `@Bean`. The `fileHandlerChain` method creates the chain of handlers:
```java
@Configuration
public class FileHandlerConfig {

    @Bean
    public FileHandler fileHandlerChain(List<FileHandler> handlers) {
        if (handlers.isEmpty()) throw new IllegalStateException(NO_HANDLERS_AVAILABLE);

        // We go through the list of handlers and create a chain
        for (int i = 0; i < handlers.size() - 1; i++) {
            handlers.get(i).setNextHandler(handlers.get(i + 1));
        }

        // Return the first handler, the beginning of the chain
        return handlers.get(0);
    }
}
```

- **List<FileHandler> handlers:** Spring automatically injects a list of all handlers registered as bean (such as TextFileHandler, DocFileHandler, PdfFileHandler)
- The method links the handlers into a chain, passing each request to the next handler if the current one cannot handle the file.

### 4. File processing service
The **FileProcessingService** is used to invoke the chain of handlers and process the files.
```java
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
```
- The rest of the code that is not directly related to the implementation of the pattern can be found in the repository files.
---
![Файловая структура паттерна Chain of Responsibility](src/main/resources/static/images/folders_edited.png)
---
# RU
# Поведенческий паттер `Chain of Responsibility` на Spring Boot
## Описание

**Chain of Responsibility** (Цепочка ответственности) — это поведенческий паттерн проектирования, который позволяет передавать запросы по цепочке обработчиков. Каждый обработчик может либо обработать запрос, либо передать его дальше по цепочке.

### Основная идея:
Запрос передается от одного обработчика к другому до тех пор, пока не найдется обработчик, способный выполнить запрос. Это обеспечивает гибкость в обработке запросов, так как новые обработчики можно легко добавить в цепочку.

### Преимущества:
- **Гибкость**: Легко добавлять или убирать обработчики, не изменяя существующую логику.
- **Модульность**: Каждый обработчик отвечает только за свою задачу.
- **Реиспользуемость**: Обработчики можно повторно использовать в других цепочках или приложениях.
- **Простота поддержки**: Код легко поддерживать и расширять.
### Сервис без цепочки ответственности:
```java
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
```
### Сервис с цепочкой ответственности:
```java
@Service
@RequiredArgsConstructor
public class FileProcessingService {

    private final FileHandler fileHandlerChain;

    public String processFile(String fileName) {
        fileHandlerChain.handleFile(fileName);
        return PROCESSING_COMPLETE + fileName;
    }
}
```
- **Обратите внимание, насколько читабельнее стал код**
---

## Реализация в Spring Boot

### 1. Абстрактный класс `FileHandler`

Этот класс является основой для всех конкретных обработчиков. Он содержит базовую логику передачи запроса дальше по цепочке.

```java
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
```
- **setNextHandler():** Устанавливает следующий обработчик в цепочке.
- **handleFile():** Основной метод обработки. Если текущий обработчик не может обработать запрос, он передает его дальше.
- **canHandleFile() и openFile():** Абстрактные методы, которые будут реализованы в каждом конкретном обработчике.

### 2. Конкретные обработчики файлов
Каждый конкретный обработчик наследует FileHandler и реализует логику для обработки определенного типа файлов.
- **Обработчик текстовых файлов `TextFileHandler`**

```java
@Component
public class TextFileHandler extends FileHandler {

    @Override
    protected boolean canHandleFile(String fileName) {
        return FileType.TXT.equals(FileType.getByExtension(fileName));
    }

    @Override
    protected void openFile(String fileName) {
        System.out.println("Opening text file: " + fileName);
    }
}
```
- **Обработчик файлов Word `DocFileHandler`**
```java
@Component
public class DocFileHandler extends FileHandler {

    @Override
    protected boolean canHandleFile(String fileName) {
        return FileType.DOC.equals(FileType.getByExtension(fileName));
    }

    @Override
    protected void openFile(String fileName) {
        System.out.println("Opening Word document: " + fileName);
    }
}
```
- **Обработчик PDF-файлов `PdfFileHandler`**
```java
@Component
public class PdfFileHandler extends FileHandler {

    @Override
    protected boolean canHandleFile(String fileName) {
        return FileType.PDF.equals(FileType.getByExtension(fileName));
    }

    @Override
    protected void openFile(String fileName) {
        System.out.println("Opening PDF file: " + fileName);
    }
}
```
### 3. Конфигурация цепочки обработчиков
В Spring Boot конфигурация цепочки ответственности может быть выполнена через компонент `@Bean`. Метод `fileHandlerChain` создает цепочку обработчиков.
```java
@Configuration
public class FileHandlerConfig {

    @Bean
    public FileHandler fileHandlerChain(List<FileHandler> handlers) {
        if (handlers.isEmpty()) throw new IllegalStateException(NO_HANDLERS_AVAILABLE);

        // Проходим по списку обработчиков и создаем цепочку
        for (int i = 0; i < handlers.size() - 1; i++) {
            handlers.get(i).setNextHandler(handlers.get(i + 1));
        }

        // Возвращаем первый обработчик, начало цепочки
        return handlers.get(0);
    }
}
```
- **List<FileHandler> handlers:** Spring автоматически передает список всех обработчиков файлов, зарегистрированных как бины (например, TextFileHandler, DocFileHandler, PdfFileHandler).
- Метод связывает обработчики в цепочку, передавая каждый запрос следующему обработчику, если текущий не может обработать файл.
### 4. Сервис для обработки файлов
Сервис **FileProcessingService** используется для вызова цепочки обработчиков и обработки файлов.
```java
@Service
@RequiredArgsConstructor
public class FileProcessingService {

    private final FileHandler fileHandlerChain;

    public String processFile(String fileName) {
        // Вызываем обработку файла через цепочку ответственности
        fileHandlerChain.handleFile(fileName);
        return PROCESSING_COMPLETE + fileName;
    }
}
```
---
![Файловая структура паттерна Chain of Responsibility](src/main/resources/static/images/folders_edited.png)
---
- Весть остальной код, не имеющий прямого отношения к реализации паттерна вы можете найти в файлах репозитория.
