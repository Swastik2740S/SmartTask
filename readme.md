<!-- Banners and Badges -->
<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-v3.5.0-brightgreen?logo=springboot&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Next.js-%20-blue?logo=next.js" alt="Next.js">
  <img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="license">
  <img src="https://img.shields.io/badge/build-passing-brightgreen" alt="Build Passing">
  <img src="https://img.shields.io/badge/PostgreSQL-%20-blue?logo=postgresql" alt="PostgreSQL">
</p>

<h1 align="center">🚀 SmartTask</h1>
<p align="center">
  <b>A modern, secure, and scalable task management system.</b><br/>
  <i>Built with Spring Boot & Next.js. Handles real-world team collaboration, security, and performance.</i>
</p>

---

## 📚 Table of Contents

- [🚩 Features](#-features)
- [🛠️ Tech Stack](#-tech-stack)
- [⚡ Quick Start](#-quick-start)
- [⚙️ Configuration](#️-configuration)
- [📖 API Documentation](#-api-documentation)
- [📂 Project Structure](#-project-structure)
- [🙌 Contribution](#-contribution)
- [📝 License](#-license)

---

## 🚩 Features

- 👤 **User Management**
  - Registration & login
  - Profile management
  - Role-based access (Admin / Project Manager / Team Lead / Member / Viewer)
  - Self-service password change & updates
  - Admin user creation, deactivation, deletion
- ✅ **Task Management**
  - Create, update, delete, and mark tasks as complete
  - Assign tasks to users
- 📁 **Project Management**
  - Create, update, delete projects
- 👫 **Team Management**
  - Create, update, delete teams & assign users
- 🔒 **Security**
  - JWT authentication, BCrypt hashed passwords, CORS for frontend integration
- 📑 **API Docs**
  - Swagger (OpenAPI) for easy API exploration
- 📈 **Performance**
  - Handles up to 30 requests per second (cloud-deployed, scalable)
---


## 🛠️ Tech Stack

| Layer      | Technologies                                                                                           |
|------------|-------------------------------------------------------------------------------------------------------|
| **Backend**| ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen) Spring Security, JWT, ModelMapper, PostgreSQL, Lombok |
| **Frontend**| ![Next.js](https://img.shields.io/badge/Next.js-13-blue) Tailwind CSS                                |
| **DevOps** | Docker, AWS EC2, Maven                                                                                |
| **APIs**   | RESTful, documented via Swagger/OpenAPI                                                               |

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





