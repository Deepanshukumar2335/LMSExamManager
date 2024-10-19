# LMSExamManager

LMSExamManager is a Spring Boot-based RESTful API designed to manage the exam enrollment process for a Learning Management System (LMS). It allows CRUD operations for Students, Subjects, and Exams, with validation and exception handling. Additionally, it features an Easter egg that provides random facts using the Numbers API.

## What are we doing in this project 

Developing a RESTful API service to manage the exam enrollment process for a Learning Management System (LMS), using MySQL for data persistence. The project handles the following functionalities:
- Manage students, subjects, and exams with proper relationships.
- Students can only register for exams after enrolling in the corresponding subject.
- Handle errors with appropriate HTTP response codes.

### Features

- **Student Management**: Add, update, delete, and retrieve students. Each student has a unique registration ID, name, a list of enrolled subjects, and registered exams.
- **Subject Management**: Manage subjects with a unique ID, name, and enrolled students.
- **Exam Registration**: Exams can only be registered for by students who have already enrolled in the relevant subject.
- **Easter Egg Feature**: A hidden endpoint that generates a random fact about numbers using the Numbers API.

## Technologies Used

- **Java 21**: The project is built using Java 21, taking advantage of modern language features.
- **Spring Boot 3.3.4**: Used for building the REST API.
- **Spring Data JPA**: For interaction with the MySQL database.
- **MySQL**: Relational database to persist data.
- **Lombok**: To reduce boilerplate code like getters, setters, and constructors.
- **JUnit**: For testing.
- **MockMvc**: To test controllers and endpoints.
- **Gradle**: For build and dependency management.

## Setup and Installation

### Prerequisites

- Java 21
- Gradle
- MySQL

### Steps to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/LMSExamManager.git
   cd LMSExamManager

2. Setup the MySQL database:

- **Create a MySQL database:**
    CREATE DATABASE lmsexam_manager;

- **Update the database configuration in src/main/resources/application.properties:**
    spring.datasource.url=jdbc:mysql://localhost:3306/lms_exam_manager
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    spring.jpa.hibernate.ddl-auto=update

3. Build and run the project:

./gradlew clean build
./gradlew bootRun

4. Access the application at http://localhost:8080.

## API Endpoints

- **Main Endpoints**

- **POST /exams/{examId}:** Registers a student for a specific exam after checking that the student is enrolled in the corresponding subject.
- **POST /students:** Create a new student.
- **GET /students/{id}:** Retrieve a student by their ID.
- **POST /subjects:** Add a new subject.
- **GET /subjects/{id}:** Retrieve a subject by its ID.
- **GET /exams/{examId}:** Get details of a specific exam.

- **Easter Egg Feature**
- **GET /hidden-feature/{number}:** Returns a random fact about the given number by calling the Numbers API.


## Project Structure

LMSExamManager/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── crio/
│   │   │           └── lmsExamManager/
│   │   │               ├── controller/
│   │   │               ├── service/
│   │   │               ├── model/
│   │   │               ├── repository/
│   ├── resources/
│   └── test/
├── build.gradle
├── README.md


## Testing

Run the unit tests using:
./gradlew test

Basic tests are implemented using JUnit and MockMvc. The tests include three unit tests to ensure the functionality of student registration, subject enrollment, and exam registration.

## Build Configuration
The project uses Gradle for dependency management. Key configurations include Java 21 as the toolchain and Spring Boot 3.3.4 for RESTful service development.

plugins {
    id 'java'
    id 'org.springframework.boot' version '3.3.4'
    id 'io.spring.dependency-management' version '1.1.6'
}

group = 'com.crio'
version = '0.0.1-SNAPSHOT'

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    compileOnly 'org.projectlombok:lombok'
    runtimeOnly 'com.mysql:mysql-connector-j'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
    useJUnitPlatform()
}

Contact Information
For issues or inquiries, reach out to deepanshukumar2335@gmail.com.











