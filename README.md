# Inditex Price Service API

This project provides an API for retrieving product pricing information based on `productId`, `brandId`, and `applicationDate`. It is designed to integrate with the Inditex ecosystem, allowing users to query the price of a product for a given date, helping businesses track pricing changes over time.
It serves as a key component of the pricing microservices that manage and expose price data for various brands and products.

## Tech Stack

- **Java 21**: The project is developed using Java 21, leveraging its latest features.
- **Spring Boot 3.4.2**: Microservice built on the Spring Boot framework for quick integration and efficient development.
- **H2 Database**: In-memory H2 database used for development and local testing.
- **Swagger (OpenAPI 3.0)**: API documentation and ease of interaction through Swagger UI.
- **JPA (Java Persistence API)**: Interaction with the database via the ORM framework Hibernate.
- **MapStruct**: Object mapping between domain models and DTOs.
- **Docker**: Deployment in containers for consistent and easy configuration.
- **API First** – The API contract is defined using OpenAPI (`api-price.yml`)

## Features

- **Price Search**: Allows searching for prices based on `productId`, `brandId`, and `applicationDate`.
- **Error Handling**: Custom exception handling like `PriceNotFoundException` and `PriceListEmptyException`.
- **Data Validation**: Use of Java validation annotations (`@NotNull`, `@Pattern`, `@Positive`) to ensure correct inputs.
- **H2 Database**: In-memory H2 database used for fast development and testing.
- **Swagger Documentation**: The API is fully documented with Swagger for easy interaction.
- **Exception Handling**: A global exception handler provides structured error responses.
- **OpenApi Generator**: The project uses OpenAPI Generator to generate API documentation and client SDKs.
- **Testing**:: Junit 5 and Mockito are used for unit and integration testing.
## API Endpoints

### 1. **GET /api/prices**
Fetches the price details of a product based on `productId`, `brandId`, and `applicationDate`.

**Query Parameters:**

| Parameter        | Type     | Required | Description                                                              |
|------------------|----------|----------|--------------------------------------------------------------------------|
| `productId`      | `Long`   | Yes      | The product identifier.                                                  |
| `brandId`        | `Long`   | Yes      | The brand identifier.                                                    |
| `applicationDate`| `String` | Yes      | The date for which the price is being queried (format: `yyyy-MM-dd'T'HH:mm:ss`). |


#### Request Example:
```http
GET /api/prices?productId=35455&brandId=1&applicationDate=2020-06-14T15:00:00
```

#### Response Example:
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "curr": "EUR"
}
```

#### Response Codes:
- **200 OK**: Successful retrieval of price details.
- **400 Bad Request**: Invalid request parameters.
- **404 Not Found**: No price found for the given parameters.
- **500 Internal Server Error**: Server error while processing the request.

## Docker Setup

To run the application using Docker, follow these steps:

1. Ensure Docker is installed and running on your machine.

2. **Build the Docker image**:
   ```bash
   docker build -t inditex-app .

3. **Run the Docker container**:
   ```bash
    docker run -p 8080:8080 inditex-price-service
    ```
### 4. Access the API

- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)  
  Web interface to explore and test the available API endpoints.

- **H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
  Web-based console to connect to the H2 in-memory database and inspect data.

### 5. Generate OpenApi Generator Files
To generate the OpenAPI files, run the following command:

```bash
mvn clean package install
```

## Author
- **Name**: Alberto Gómez Barral
- **Role**: Java Back-End Developer
- **Experience**: Expert in Spring Boot, Microservices, AWS, and Java 8-21



