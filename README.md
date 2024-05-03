# HR and Payroll System

## Description:
This project is a Java-based application designed to manage human resources and payroll data. It includes functionalities for managing employee details, departments, and payroll calculations. The system allows for employee management tasks such as adding, updating, and deleting employee records, as well as handling payroll operations.

## Features:
- **Employee Management**: Add, update, and delete employees.
- **Department Management**: Manage departments within the organization.
- **Payroll Processing**: Calculate and manage payroll details for employees.
- **Data Persistence**: Store and retrieve employee, department, and payroll data from disk.

## Technologies Used:
- **Java**: Core programming language.
- **JavaFX**: For creating the user interface.
- **Maven**: Dependency management and project build.
- **JUnit**: For unit testing components.

## Project Structure:
- **`src/main/java/`**: Contains the Java source files for the application.
- **`Model/`**: Domain models and business logic.
- **`UI/`**: JavaFX user interface classes.
- **`Ulti/`**: Utility classes for data management.
- **`src/main/resources/`**: Resources for the application, such as FXML files.
- **`pom.xml`**: Maven configuration file.

## Setup and Installation:
1. **Prerequisites**:
    - Java JDK 11 or higher.
    - Maven installed on your system.
2. **Running the Application**:
    - Navigate to the project directory.
    - Use Maven to build and run the project:  
    `mvn clean install`
    `mvn javafx:run`

## Usage:
The application provides a graphical user interface to interact with the HR and Payroll system. From the main menu, users can choose to:

- Add new employees or edit existing ones.
- Create or modify departments.
- Process payroll and view payroll records.
- Exit the application.

## Sample Login Credentials:
- **Admin Login**:  
    - **Username**: Admin  
    - **Password**: admin  
- **Manager Login**:  
  - **Username**: Manager  
  - **Password**: manager    
