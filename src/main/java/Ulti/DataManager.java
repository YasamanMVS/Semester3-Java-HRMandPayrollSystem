package Ulti;

import Model.*;

import java.io.*;
import java.util.List;

public class DataManager {
    public static final String EMPLOYEE_FILE = "employees.dat";
    public static final String DEPARTMENT_FILE = "departments.dat";
    public static final String PAYROLL_FILE = "payrolls.dat";

    // Methods for Employee data
    public static void saveEmployeeData(List<Employee> employees) throws IOException {
        saveData(employees, EMPLOYEE_FILE);
    }

    public static List<Employee> loadEmployeeData() throws IOException, ClassNotFoundException {
        List<Employee> employees = (List<Employee>) loadData(EMPLOYEE_FILE);
        return employees;
    }

    // Methods for Department data
    public static void saveDepartmentData(List<Department> departments) throws IOException {
        saveData(departments, DEPARTMENT_FILE);
    }

    public static List<Department> loadDepartmentData() throws IOException, ClassNotFoundException {
        List<Department> departments = (List<Department>) loadData(DEPARTMENT_FILE);
        return departments;
    }

    // Methods for Payroll data
    public static void savePayrollData(List<Payroll> payrolls) throws IOException {
        saveData(payrolls, PAYROLL_FILE);
    }

    public static List<Payroll> loadPayrollData() throws IOException, ClassNotFoundException {
        List<Payroll> payrolls = (List<Payroll>) loadData(PAYROLL_FILE);
        return payrolls;
    }

    // Generic save and load methods
    private static void saveData(Object data, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(data);
        }
    }

    private static List<?> loadData(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<?>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Log the exception for better debugging
            System.err.println("Error loading data from " + filename + ": " + e);
            throw e; // Rethrow the exception to notify the caller
        }
    }
}
