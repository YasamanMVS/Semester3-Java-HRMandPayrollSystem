package Model;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.io.Serializable;

public class Department implements Serializable{
    private static final long serialVersionUID = 1L;
    private static int nextID = 1;
    private int departmentID;
    private String name;
    private List<Employee> employees;

    // Constructor
    public Department (String name) {
        this.departmentID = nextID;
        nextID++;
        this.name = name;
        this.employees = new ArrayList<>();
    }

    // Getters and Setters
    public int getDepartmentID() {
        return departmentID;
    }

    public static void setNextID(int nextID) {
        Department.nextID = nextID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty!");
        }
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    // Department methods
    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null!");
        }
        employees.add(employee);
    }
    public void removeEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null!");
        }
        employees.remove(employee);
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(this.name);
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentID=" + departmentID +
                ", name='" + name + '\'' +
                ", employees=" + employees +
                '}';
    }
}
