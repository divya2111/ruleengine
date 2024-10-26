# RULE ENGINE AST

## Table of Contents
- [Description] (#description)
- [Technologies Used] (#technologies)
- [Installation](#installation)
- [Design Choices](#design-choices)
- [Dependencies](#dependencies)
- [Rule Structure](#rule-structure)

## Description
This project implements a Rule Engine that utilizes an Abstract Syntax Tree (AST) to evaluate rules defined in a structured format. The rule engine processes these rules to determine actions based on given conditions, making it suitable for various applications such as business logic validation, dynamic decision-making, and complex event processing.

## Technologies Used
- **Backend**: Spring Boot
- **Frontend**: JSP, HTML, CSS, JavaScript
- **Database**: MySQL
- **Build Tool**: Maven


## Installation
# Prerequisites
- **Java JDK**: Version 11 or higher
  - [Download Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- **Maven**: Version 3.6.0 or higher
  - [Download Maven](https://maven.apache.org/download.cgi)
- **MySQL**: Version 5.7 or higher
  - [Download MySQL](https://dev.mysql.com/downloads/mysql/)
  
### Steps to Install
**Clone the repository:**
   ```bash
   git clone https://github.com/divya2111/ruleengine.git
   cd ruleengine
   
   Install Required Software: Ensure Java, Maven, and MySQL are installed as mentioned in the prerequisites.
   
###Configure MySQL Database
   
   USE rule_engine
   
   CREATE TABLE rules (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rule_string VARCHAR(255) NOT NULL,
    ast_json JSON NOT NULL
);

##Build Instructions
 **Run the Application**:
   You can run the application using one of the following methods:

   **Option A: Using Eclipse**
   - Open Eclipse and import the project:
     - Go to **File** > **Import** > **Existing Maven Projects** and select the cloned repository.
   - After the project is imported, right-click on the project in the **Project Explorer**.
   - Select **Run As** > **Spring Boot App** (or **Java Application** if it doesn't appear).

   **Option B: Using Command Line**
   If you prefer to run the application from the command line, use:
   ```bash
   ./mvnw spring-boot:run
   
  Access the Application: http://localhost:8080/
   
##Design Choices
Architecture: The application follows a layered architecture with separate layers for controllers, services, and repositories to maintain separation of concerns.
Database Design: Designing a database for a rule engine that utilizes an Abstract Syntax Tree (AST) requires careful consideration of how rules are represented and how they relate to other entities in the system

##Dependencies
To set up and run the application, you will need the following dependencies:

Required Software
Java JDK: Version 11 or higher
Maven: Version 3.6.0 or higher
MySQL: Version 5.7 or higher
Optional (if using Docker)
Docker: Required if you want to run services in containers.
Download Docker
Spring Boot Dependencies
   The following dependencies are automatically managed by the Spring Boot application:

- **Spring Web**: For building web applications and REST APIs.
- **Spring Data JPA**: For database interaction using Java Persistence API.
- **MySQL Connector/J**: JDBC driver for MySQL, included in the `pom.xml` fil
   
  
## Rule Structure
{
  "rules": [
    {
      "id": 1,
      "condition": {
        "field": "temperature",
        "operator": ">",
        "value": 30
      },
      "action": {
        "type": "alert",
        "message": "Temperature exceeds 30 degrees."
      }
    }
  ]
}

