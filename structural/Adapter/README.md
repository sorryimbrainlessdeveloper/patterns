# Пример паттерна Адаптер на Spring Boot

## Описание

Паттерн **Adapter** (адаптер) — это структурный шаблон проектирования, который позволяет объектам с несовместимыми интерфейсами работать вместе. Он преобразует интерфейс одного класса в интерфейс, понятный другим классам. В Spring Boot это особенно полезно для интеграции с внешними системами или библиотеками, когда их интерфейсы отличаются от внутреннего API приложения.

В этом проекте мы рассмотрим использование паттерна **Adapter** в реальной задаче: интеграцию с внешним API для получения информации о товарах.

## Структура проекта

- **Product**: Интерфейс, который описывает продукт внутри нашего приложения.
- **ExternalProduct**: Класс, представляющий товар, полученный от внешнего API (с другим набором полей).
- **ProductAdapter**: Адаптер, который преобразует объект `ExternalProduct` в объект, совместимый с интерфейсом `Product`.
- **ProductService**: Сервис, который отвечает за получение данных о продукте и их адаптацию.
- **ProductController**: REST-контроллер, который предоставляет клиенту API для работы с продуктами.

## Пример сценария

Представьте, что наше приложение ожидает информацию о товарах в формате `Product`, но внешний API возвращает данные в формате `ExternalProduct`. В этом случае класс `ProductAdapter` берёт на себя задачу преобразования данных из одного формата в другой, сохраняя совместимость приложения.

## Как работает паттерн Adapter

1. **Интерфейс `Product`**: Описывает продукт, который наше приложение использует.
    ```java
    public interface Product {
        String getName();
        BigDecimal getPrice();
        String getDescription();
    }
    ```

2. **Внешний продукт `ExternalProduct`**: Возвращается сторонним API, но его структура не соответствует интерфейсу `Product`.
    ```java
    public class ExternalProduct {
        private String productName;
        private double cost;
        private String details;

        // Геттеры и конструктор
    }
    ```

3. **Адаптер `ProductAdapter`**: Преобразует данные `ExternalProduct` в `Product`.
    ```java
    public class ProductAdapter implements Product {
        private final ExternalProduct externalProduct;

        public ProductAdapter(ExternalProduct externalProduct) {
            this.externalProduct = externalProduct;
        }

        @Override
        public String getName() {
            return externalProduct.getProductName();
        }

        @Override
        public BigDecimal getPrice() {
            return BigDecimal.valueOf(externalProduct.getCost());
        }

        @Override
        public String getDescription() {
            return externalProduct.getDetails();
        }
    }
    ```

4. **Сервис `ProductService`**: Использует адаптер для получения данных о продукте из внешнего источника.
    ```java
    @Service
    public class ProductService {
        public Product getProductFromExternalApi() {
            ExternalProduct externalProduct = new ExternalProduct("Laptop", 1500.0, "High-performance laptop");
            return new ProductAdapter(externalProduct);
        }
    }
    ```

5. **Контроллер `ProductController`**: Предоставляет клиенту данные через API.
    ```java
    @RestController
    @RequestMapping("/products")
    public class ProductController {

        private final ProductService productService;

        public ProductController(ProductService productService) {
            this.productService = productService;
        }

        @GetMapping("/external")
        public ResponseEntity<Product> getExternalProduct() {
            Product product = productService.getProductFromExternalApi();
            return ResponseEntity.ok(product);
        }
    }
    ```

## Запуск проекта

1. Клонируйте репозиторий:
    ```bash
    git clone https://github.com/your-repo/spring-adapter-pattern-example.git
    ```

2. Откройте проект в вашей IDE и запустите его как Spring Boot приложение:
    ```bash
    ./mvnw spring-boot:run
    ```

3. Откройте браузер и перейдите по адресу:
    ```
    http://localhost:8080/products/external
    ```

Вы получите ответ в формате JSON с адаптированными данными продукта:
```json
{
  "name": "Laptop",
  "price": 1500.0,
  "description": "High-performance laptop"
}
