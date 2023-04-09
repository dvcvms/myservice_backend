# myservice_backend

## Features

- [x] User registration with email verification
- [x] Authentication with JSON Web Token (JWT)
- [x] Role-based authorization 
- [x] Password encryption using BCrypt
- [x] Email sending mechanism

## Technologies

- [x] Spring Boot 3
- [x] Spring Security 6
- [x] JSON Web Token (JWT)
- [x] BCrypt
- [x] Spring JavaMail API
- [x] Email verification with expiry
- [x] Spring Data JPA
- [x] Maven

## Getting Started

### You need to set your database setting in application.properties:

```
spring.datasource.driver-class-name=
spring.datasource.url=
spring.jpa.hibernate.ddl-auto=
spring.datasource.username=
spring.datasource.password=
```

### Another way you can use these properties. Replace application.properties setting with these:

```
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:file:./myservice_db
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.generate-ddl=true
```

### To build and run this project, follow these steps:

- Clone the repository: `git clone https://github.com/dvcvms/myservice_backend.git`
- Navigate to the project directory: `cd myservice`
- Build the project: `mvn clean install`
- Run the project: `mvn spring-boot:run`

#### The application will be available at http://localhost:8080/
