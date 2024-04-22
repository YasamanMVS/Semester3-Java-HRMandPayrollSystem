package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Employee implements Serializable {
    // serialVersionUID field
    private static final long serialVersionUID = 1L;

    // Attributes of Employee class
    private static int nextId = 1;
    private int id;
    private String firstName;
    private String lastName;
    private String department;
    private String position;
    private double salary;
    

    // Constructor
    public Employee(String firstName, String lastName, String department, String position, double salary) {
        id = nextId;
        nextId++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.position = position;
        this.salary = salary;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public static void setNextId(int id) {
        Employee.nextId = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setName(String firstName, String lastName) {
        if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty!");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative!!");
        }
        this.salary = salary;
    }

    // Employee methods
    public double calculateYearlySalary() {
        return salary * 12;
    }

    public StringProperty firstNameProperty() {
        return new SimpleStringProperty(this.firstName);
    }

    public StringProperty lastNameProperty() {
        return new SimpleStringProperty(this.lastName);
    }

    public StringProperty departmentProperty() {
        return new SimpleStringProperty(this.department);
    }

    public StringProperty positionProperty() {
        return new SimpleStringProperty(this.position);
    }

    public DoubleProperty salaryProperty() {
        return new SimpleDoubleProperty(this.salary);
    }
}
