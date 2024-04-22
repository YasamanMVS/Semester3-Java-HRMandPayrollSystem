package Model;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private List<Employee> employees;
    public EmployeeManager() {
        this.employees = new ArrayList<>();
    }

    // validate input
    public boolean validateInput(String salary) {
        try {
            double salaryDouble = Double.parseDouble(salary);
            // Check if salary is a positive number
            if (salaryDouble < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            // salary is not a double
            return false;
        }
        // If all checks pass, return true
        return true;
    }
    
    // Add new employee to the list
    public Employee addEmployee(String firstName, String lastName, String department, String position, String salary) {
        if (!validateInput(salary)) {
            throw new IllegalArgumentException("Salary must be a positive number!");
        }
        Employee newEmployee = new Employee(firstName, lastName, department, position, Double.parseDouble(salary));
        this.employees.add(newEmployee);
        return newEmployee;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    // Update an existing employee's details
    public void updateEmployee(Employee updatedEmployee) {
        for (Employee employee : employees) {
            if (employee.getId() == updatedEmployee.getId()) {
                employee.setName(updatedEmployee.getFirstName(), updatedEmployee.getLastName());
                employee.setDepartment(updatedEmployee.getDepartment());
                employee.setPosition(updatedEmployee.getPosition());
                employee.setSalary(updatedEmployee.getSalary());
                return;
            }
        }
        throw new IllegalArgumentException("Employee with ID " + updatedEmployee.getId() + " not found!!");
    }

    // Retrieve an employee by their ID
    public Employee getEmployee(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        throw new IllegalArgumentException("Employee with ID " + id + " not found!!");
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    // Delete an employee from the list
    public void deleteEmployee(Employee employee) {
        this.employees.remove(employee);
    }

    public void setNextId(int id) {
        Employee.setNextId(id);
    }
}
