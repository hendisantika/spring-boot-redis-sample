# Spring Boot Redis Sample

[![Java CI with Maven](https://github.com/hendisantika/spring-boot-redis-sample/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/spring-boot-redis-sample/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-brightgreen?logo=springboot)
![Dependabot](https://img.shields.io/badge/Dependabot-enabled-blue?logo=dependabot)

A comprehensive Spring Boot 4.1.2 application demonstrating Redis integration with caching, data persistence, and
RESTful APIs.

## Features

- Spring Boot 4.1.2 with Java 25
- Redis Stack integration for data storage and caching
- Spring Data Redis for repository management
- Spring Security with BCrypt password encoding
- RESTful API endpoints for Books and Users management
- Redis caching with configurable TTL
- Docker Compose integration for easy Redis setup
- Sample data initialization on startup
- Testcontainers for integration testing
- GitHub Actions CI on JDK 25 with automated Dependabot updates

## Technologies

- **Spring Boot**: 4.1.2
- **Java**: 25
- **Redis**: Redis Stack (latest)
- **Spring Data Redis**: Repository pattern implementation
- **Spring Security**: User authentication and authorization
- **Spring Validation**: Input validation
- **Jackson 3**: JSON (de)serialization (`tools.jackson.*`)
- **Lombok**: Reduce boilerplate code
- **Docker Compose**: Container orchestration
- **Testcontainers**: 2.x for integration testing
- **GitHub Actions** & **Dependabot**: CI and dependency updates

## Prerequisites

- Java 25 or higher
- Docker and Docker Compose (also required by the Testcontainers tests)
- Maven 3.9+ (optional, the bundled Maven Wrapper `./mvnw` is recommended)

## Project Structure

```
spring-boot-redis-sample/
├── src/main/java/
│   └── id/my/hendisantika/springbootredissample/
│       ├── SpringBootRedisSampleApplication.java  # Main application class
│       ├── boot/                                  # Data initialization
│       │   ├── CreateBookRatings.java
│       │   ├── CreateBooks.java
│       │   ├── CreateRoles.java
│       │   └── CreateUsers.java
│       ├── controller/                            # REST Controllers
│       │   ├── BookController.java
│       │   └── UserController.java
│       ├── model/                                 # Domain models
│       │   ├── Book.java
│       │   ├── BookRating.java
│       │   ├── Category.java
│       │   ├── Role.java
│       │   └── User.java
│       └── repository/                            # Redis repositories
│           ├── BookRatingRepository.java
│           ├── BookRepository.java
│           ├── CategoryRepository.java
│           ├── RoleRepository.java
│           └── UserRepository.java
├── src/main/resources/
│   ├── application.properties                     # Application configuration
│   └── data/books/                                # Sample book data
├── .github/
│   ├── dependabot.yml                             # Dependabot configuration
│   └── workflows/maven.yml                        # GitHub Actions CI workflow
├── compose.yaml                                   # Docker Compose configuration
└── pom.xml                                        # Maven dependencies
```

## Configuration

### Application Properties (application.properties:1)

```properties
spring.application.name=bookstore
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.data.redis.password=${REDIS_PASSWORD:53cret}
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
app.numberOfRatings=5000
app.ratingStars=5
```

### Redis Configuration (compose.yaml:1)

The application uses Redis Stack with the following configuration:

- Port: 6379
- Password: 53cret
- Persistence: AOF (Append-Only File)
- Data directory: ./data

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/hendisantika/spring-boot-redis-sample.git
cd spring-boot-redis-sample
```

### 2. Start Redis with Docker Compose

```bash
docker compose up -d
```

### 3. Build the application

```bash
./mvnw clean install
```

### 4. Run the application

```bash
./mvnw spring-boot:run
```

The application will start on http://localhost:8080

## API Endpoints

### Books API

#### Get all books (with pagination)

```bash
GET /api/books?page=0&size=10
```

Response:

```json
{
  "total": 2,
  "page": 0,
  "pages": 1,
  "books": [
    ...
  ]
}
```

#### Get book by ISBN

```bash
GET /api/books/{isbn}
```

#### Get all categories

```bash
GET /api/books/categories
```

### Users API

#### Get users by email

```bash
GET /api/users?email=yuji@yopmail.com
```

## Caching

The application uses Redis caching with the following configuration (SpringBootRedisSampleApplication.java:36):

- Cache TTL: 1 hour
- Cache name prefix: Package name
- Null values caching: Disabled

### Cached Endpoints

- `/api/books` - Cached with key: `page-size`
- `/api/books/categories` - Cached

## Data Models

### Book (Book.java:1)

- ISBN (ID)
- Title, Subtitle, Description
- Language, Page Count
- Price, Currency
- Authors (Set)
- Categories (Set)
- Thumbnail, Info Link

### User (User.java:1)

- ID
- Name, Email
- Password (BCrypt encrypted)
- Roles (Set)

### Category (Category.java:1)

- ID
- Name

### BookRating (BookRating.java:1)

- ID
- User reference
- Book reference
- Rating (1-5 stars)

## Testing

Run tests with:

```bash
./mvnw test
```

The project includes Testcontainers 2.x for integration testing with Redis, so Docker must be running.

## Continuous Integration

GitHub Actions ([`.github/workflows/maven.yml`](.github/workflows/maven.yml)) runs on every push and pull request to
`main` (and manually via *workflow_dispatch*):

- Sets up Temurin JDK 25 with Maven dependency caching
- Builds and tests the project with `./mvnw -B verify`
- On pushes to `main`, submits the Maven dependency graph to GitHub to improve Dependabot alerts

## Dependency Updates

Dependabot ([`.github/dependabot.yml`](.github/dependabot.yml)) keeps dependencies up to date:

| Ecosystem      | Schedule                    | Grouping                                                  | Commit prefix      |
|----------------|-----------------------------|-----------------------------------------------------------|--------------------|
| Maven          | Daily, 05:00 (Asia/Jakarta) | Spring Boot, Testcontainers, other minor & patch updates  | `build(deps)`      |
| GitHub Actions | Weekly, Monday              | All actions in a single PR                                | `ci(deps)`         |
| Docker Compose | Weekly, Monday              | Images in `compose.yaml`                                  | `build(deps)`      |

## Docker Compose Services

The `compose.yaml` defines a Redis Stack service:

- Image: redis/redis-stack:latest
- Container name: redis
- Ports: 6379:6379
- Volume: ./data:/data
- Password protected: 53cret

## Development

### Spring Boot DevTools

The application includes Spring Boot DevTools for:

- Automatic application restart on code changes
- LiveReload server on port 35729

### Sample Data

On startup, the application automatically creates:

- 2 Roles (admin, customer)
- 5 Users
- 2 Books
- 5000 Book Ratings

## Building for Production

```bash
./mvnw clean package -DskipTests
java -jar target/spring-boot-redis-sample-0.0.1-SNAPSHOT.jar
```

## License

This project is for educational purposes.

## Author

- Name: Hendi Santika
- Link: s.id/hendisantika
- Email: hendisantika@yahoo.co.id
- Telegram: @hendisantika34

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
