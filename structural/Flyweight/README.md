## ENG

# Spring Boot Example with Flyweight Pattern for Database Optimization

### Project Description

This project demonstrates the use of the **Flyweight** design pattern for optimizing database interactions in a **Spring
Boot** application. By applying the Flyweight pattern, we reduce the number of database calls by caching already loaded
objects, which significantly decreases the load on the database and speeds up application response time.

To illustrate this, we use the `CategoryData` entity, whose data is cached after the first access. If a category has already
been loaded, the system retrieves it from the cache instead of making a repeated database call. For testing, we use a
PostgreSQL database, running through **Testcontainers**.

## Technology Stack

- **Java 17**
- **Spring Boot** 2.x/3.x
- **Spring Data JPA**
- **PostgreSQL**
- **Testcontainers** (for integration testing)
- **Maven**

## Environment Requirements

- Java Development Kit (JDK) 17 or higher
- Docker (to run the PostgreSQL container via Testcontainers)
- Maven 3.x

## Installation and Running the Project

1. **Clone the repository**:

   ```bash
   git clone https://github.com/sorryimbrainlessdeveloper/patterns.git
   cd patterns
   ```

2. **Build the project**:

   Build the project using Maven:

   ```bash
   mvn clean install
   ```

3. **Run the application**:

   Start the application:

   ```bash
   mvn spring-boot:run
   ```

4. **Run tests**:

   Execute the integration and unit tests that check the caching logic and interaction with PostgreSQL via
   Testcontainers:

   ```bash
   mvn test
   ```

## Project Structure

```
flyweight/
├── src
│   ├── main
│   │   ├── java/com/example/app
│   │   │   ├── FlyweightApplication.java
│   │   │   ├── entity/Category.java
│   │   │   ├── entity/CategoryData.java
│   │   │   ├── exception/CategoryNotFoundException.java
│   │   │   ├── exception/CategoryDataNotFoundException.java
│   │   │   ├── flyweight_implemntation/CategoryDataFlyweightFactory.java
│   │   │   ├── repository/CategoryDataRepository.java
│   │   │   ├── repository/CategoryRepository.java
│   │   │   ├── service/CategoryService.java
│   │   │   └── utils/StaticMessages.java
│   │   └── resources/application.yml                   
│   └── test
│       ├── java/com/example/app/service
│       │   └── CategoryServiceIntegrationTest.java
│       └── resources/application-test.yml
└── README.md                                                         
```

## Functional Description

### `Category` Entity

The `Category` represents a simple category of goods or services. It is stored in the database and has the following
properties:

- `id` — unique identifier
- `category data` — nested object
-
The `CategoryData` represent a detailed description of goods and services. It is stored in the database and has the following
properties:

- `id` — unique identifier
- `category name` - category name

### `CategoryRepository` Interface

`CategoryRepository` is an interface extending `JpaRepository` and provides standard methods for interacting with the
database.

### `CategoryDataRepository` Interface

`CategoryDataRepository` is an interface extending `JpaRepository` and provides standard methods for interacting with the
database.

### `CategoryService`

`CategoryService` implements caching logic for optimizing requests. It uses the `categoryDataCache` to store categories
loaded from the database. The logic of the service is as follows:

- Find the category with the specified `id`.
- If the detailed description of the category is already in the cache, then take it from the cache and assign it to the current category.
- If the detailed description of the category is not in the cache, the service downloads it from the database, adds it to the cache and returns it.

Example of the caching method:

```java
public Category getCategoryById(Long categoryId) {
   final Category category = categoryRepository.findById(categoryId)
           .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND.formatted(categoryId)));

   final CategoryData categoryData = CategoryDataFlyweightFactory
           .getCategory(category.getId(), categoryDataRepository);
   category.setCategoryData(categoryData);

   return category;
}
```

### Test Configuration

`application-test.yml` contains the database settings for tests that use **Testcontainers** to run a PostgreSQL
container.

Example configuration:

```yaml
spring:
  datasource:
    url: jdbc:tc:postgresql:15:///testdb
    driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
    username: test
    password: test
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
```

## Integration Tests

Integration tests check the correctness of caching and interaction with the PostgreSQL database.

### Example of an Integration Test

```java

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryServiceIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCategoryCaching() {
        // Check caching category data of category with id 1
        Category category1 = categoryService.getCategoryById(1L);
        assertNotNull(category1);

        // Check that the repeated request takes the category with data from the cache
        Category category1Cached = categoryService.getCategoryById(1L);
        assertEquals(category1, category1Cached);
    }
}
```

## Optimization and Extension Notes

1. **Adding other types of caches**: You can replace the simple `HashMap` with a more complex cache, such as one that
   supports TTL, using `Caffeine` or `Ehcache`.

2. **Extending the service**: Add methods for updating and deleting categories, and in those methods, do not forget to
   also update the cache.

3. **Caching logging**: Implement logging to track when data is taken from the cache and when it is taken from the
   database.
---
This application demonstrates a basic implementation of the Flyweight pattern in Spring Boot using JPA and PostgreSQL,
providing data isolation for integration tests through Testcontainers.
---

## RU

# Пример Spring Boot приложения с паттерном Flyweight для оптимизации запросов к базе данных

### Описание проекта

Этот проект демонстрирует использование паттерна проектирования **Flyweight** для оптимизации работы с
базой данных в приложении на **Spring Boot**. Применяя паттерн Flyweight, мы уменьшаем количество обращений к базе
данных за счёт кэширования уже загруженных объектов, что позволяет значительно сократить нагрузку на базу данных и
ускорить время ответа приложения.

Для демонстрации использована сущность `CategoryData`, данные которой кэшируются после первого обращения. Если
CategoryData уже была загружена, система использует её из кэша вместо повторного запроса к базе данных. Для тестирования
используется база данных PostgreSQL, запускаемая через **Testcontainers**.

## Стек технологий

- **Java 17**
- **Spring Boot** 2.x/3.x
- **Spring Data JPA**
- **PostgreSQL**
- **Testcontainers** (для интеграционного тестирования)
- **Maven**

## Требования к окружению

- Java Development Kit (JDK) 17 или выше
- Docker (для запуска контейнера PostgreSQL через Testcontainers)
- Maven 3.x

## Установка и запуск проекта

1. **Клонируйте репозиторий**:

   ```bash
   git clone https://github.com/sorryimbrainlessdeveloper/patterns.git
   cd patterns
   ```

2. **Сборка проекта**:

   Соберите проект с помощью Maven:

   ```bash
   mvn clean install
   ```

3. **Запуск приложения**:

   Запустите приложение:

   ```bash
   mvn spring-boot:run
   ```

4. **Запуск тестов**:

   Выполните интеграционные и юнит-тесты, которые проверяют логику кэширования и взаимодействие с PostgreSQL через
   Testcontainers:

   ```bash
   mvn test
   ```

## Структура проекта

```
flyweight/
├── src
│   ├── main
│   │   ├── java/com/example/app
│   │   │   ├── FlyweightApplication.java
│   │   │   ├── entity/Category.java
│   │   │   ├── entity/CategoryData.java
│   │   │   ├── exception/CategoryNotFoundException.java
│   │   │   ├── exception/CategoryDataNotFoundException.java
│   │   │   ├── flyweight_implemntation/CategoryDataFlyweightFactory.java
│   │   │   ├── repository/CategoryDataRepository.java
│   │   │   ├── repository/CategoryRepository.java
│   │   │   ├── service/CategoryService.java
│   │   │   └── utils/StaticMessages.java
│   │   └── resources/application.yml                   
│   └── test
│       ├── java/com/example/app/service
│       │   └── CategoryServiceIntegrationTest.java
│       └── resources/application-test.yml
└── README.md
```

## Описание функционала

### Сущность `Category`

`Category` представляет собой простую категорию товаров или услуг. Она хранится в базе данных и имеет следующие
свойства:

- `id` — уникальный идентификатор
- `category data` — вложенный объект

`CategoryData` представляет собой детальное описание товаров или услуг. Она хранится в базе данных и имеет следующие
свойства:

- `id` — уникальный идентификатор
- `category name` - название категории

### Интерфейс `CategoryRepository`

`CategoryRepository` — это интерфейс, расширяющий `JpaRepository` и предоставляющий стандартные методы для
взаимодействия с базой данных.

### Интерфейс `CategoryDataRepository`

`CategoryDataRepository` - это интерфейс, расширяющий `JpaRepository` и предоставляющий стандартные методы для
взаимодействия с базой данных.

### Сервис `CategoryService`

`CategoryService` реализует логику кэширования для оптимизации запросов. Он использует кэш `categoryDataCache`, в котором
хранятся подробные описания категорий, загруженные из базы данных. Логика работы сервиса следующая:

- Находим категрию с указанным `id`.
- Если подробное описание категории уже есть в кэше, то берем ее из кэша и присваиваем текущей категории.
- Если подробное описание категории отсутствует в кэше, сервис загружает её из базы данных, добавляет в кэш и возвращает.

Пример метода кэширования:

```java
public Category getCategoryById(Long categoryId) {
   final Category category = categoryRepository.findById(categoryId)
           .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND.formatted(categoryId)));

   final CategoryData categoryData = CategoryDataFlyweightFactory
           .getCategory(category.getId(), categoryDataRepository);
   category.setCategoryData(categoryData);

   return category;
}
```

### Конфигурация для тестов

`application-test.yml` содержит настройки базы данных для тестов, которые используют **Testcontainers** для запуска
контейнера PostgreSQL.

Пример конфигурации:

```yaml
spring:
  datasource:
    url: jdbc:tc:postgresql:15:///testdb
    driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
    username: test
    password: test
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
```

## Интеграционные тесты

Интеграционные тесты проверяют корректность работы кэширования и взаимодействия с базой данных PostgreSQL.

### Пример интеграционного теста

```java

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryServiceIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCategoryCaching() {
        // The first request to the category with id 1 - should be loaded from the database and cached

        Category category1 = categoryService.getCategoryById(1L);
        assertNotNull(category1);

        // Repeated request to the same category - must be taken from cache
        Category category1Cached = categoryService.getCategoryById(1L);
        assertEquals(category1, category1Cached);
    }
}
```

## Заметки по оптимизации и расширению

1. **Добавление других типов кэшей**: Вы можете заменить простой `HashMap` на более сложный кэш, например, с поддержкой
   TTL, используя `Caffeine` или `Ehcache`.

2. **Расширение сервиса**: Добавьте методы для обновления и удаления категорий, и в этих методах не забывайте также
   обновлять и кэш.

3. **Логирование кэширования**: Внедрите логирование, чтобы отслеживать, когда данные берутся из кэша, а когда — из базы
   данных.

---

Это приложение демонстрирует простейшую реализацию паттерна Flyweight на Spring Boot с использованием JPA и PostgreSQL,
обеспечивая изоляцию данных для интеграционных тестов через Testcontainers.
