# SmartTask

**SmartTask** is a modern, secure, and scalable task management system built with Spring Boot and designed for seamless integration with a Next.js frontend.  
It features robust user authentication, role-based access control, and comprehensive task, project, and team management.

---

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [License](#license)

---

## Features

- **User Management**
    - Registration, login, and profile management
    - Role-based access (Admin, Member)
    - Self-service password change and profile updates
    - Admin-only user creation, deactivation, and deletion
- **Task Management**
    - Create, update, delete, and mark tasks as complete
    - Assign tasks to users
- **Project Management**
    - Create, update, and delete projects
- **Team Management**
    - Create, update, and delete teams
    - Assign users to teams
- **Security**
    - JWT-based authentication
    - Secure password storage with BCrypt
    - CORS configuration for frontend integration
- **API Documentation**
    - Swagger (OpenAPI) documentation for easy API exploration

---

## Technologies

- **Backend**
    - Spring Boot 3.5.0
    - Spring Security
    - JWT (JSON Web Tokens)
    - ModelMapper
    - PostgreSQL
    - Lombok
- **Frontend**
    - Next.js (JavaScript/Tailwind CSS)
- **Build & Deployment**
    - Maven
    - Docker (optional)

---

## Installation

1. **Clone the repository**
   git clone https://github.com/Swastik2740S
   cd smarttask


2. **Set up the database**
- Install PostgreSQL
- Create a database named `smarttask`
3. **Configure the application**
- Update `application.properties` or `application.yml` with your database credentials
- Example:
  ```
  spring.datasource.url=jdbc:postgresql://localhost:5432/smarttask
  spring.datasource.username=postgres
  spring.datasource.password=yourpassword
  ```
4. **Build and run the application**
- mvn clean install
- mvn spring-boot:run

5. **Access the application**
- API: [http://localhost:8080](http://localhost:8080)
- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## Configuration

- **JWT Secret:** Set in `application.properties`:
  jwt.secret=your-secret-key

- **CORS:** Configured in `SecurityConfig` to allow your frontend origin (e.g., `http://localhost:3000`)
- **Date Format:** ISO 8601 or custom date formats via `@JsonFormat` annotation in DTOs

---

## API Documentation

SmartTask provides comprehensive API documentation using Swagger (OpenAPI).  
Access the Swagger UI at:

http://localhost:8080/swagger-ui/index.html


---

## Project Structure

```plaintext
smarttask/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/smarttask/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── enums/
│   │   │       ├── exception/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       └── service/
│   │   └── resources/
│   │       └── application.properties
├── README.md
└── pom.xml


```

## License

This project is licensed under the **MIT License**.
```
MIT License

Copyright (c) 2025 Swastik Verma

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

**Thank you for using SmartTask!**





