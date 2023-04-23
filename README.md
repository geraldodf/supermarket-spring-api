## Supermarket Spring API

The Supermarket Spring API is a RESTful API designed to serve web clients with a focus on the supermarket business. It
allows users to perform basic CRUD (Create, Read, Update, and Delete) operations on products available for sale, manage
stock quantities, calculate the total purchase value, among other features that are gradually being developed.

This project is based on the Spring ecosystem and leverages the following main modules:

- Spring Boot;
- Spring Data;
- Spring Web;

The application was designed based on the Layered Architecture, where the main components are:

Resources: to receive HTTP requests in publicly exposed endpoints that carry JSON objects;
Services: for business validations and transaction control;
Repositories: for data persistence.
All these layers are being injected by the framework via dependency injection. The persistence layer uses Spring Data's
standard repository methods, as well as customized JPQL methods to perform more specific queries, such as retrieving a
list filtered by product type and a character contained in the product name.

All endpoints were mapped in the RESTful maturity model based on resources and identified via HTTP verbs.

The "Products" layer is the most advanced and closest to completion, with mature test coverage and advanced features.

The MySQL database was used to store data, where all tables were manually created from scripts.

This project is still under development and was entirely created by me.

Technologies Used

- Spring Boot
- Spring Data
- Spring Web
- Java 17
- Postgres
- Lombok
- Layered Architecture
- Postman
- JSON
- RESTful
- Git
- JUnit
- Mockito
- Flyway
- Docker

### Two ways to run the project

#### 1) Development environment

Follow these steps to run the application with just the dockerized database, and with the application running in your
IDE. (Must have Java and Maven installed):

- Run this command to create the database:

######  docker run --name postgres-supermarket -e POSTGRES_PASSWORD=admin123 -e POSTGRES_DB=supermarketdb -p 5433:5432 -d
postgres

- Then just run it in your IDE, and it will be available to access port 8080/api in your browser.
