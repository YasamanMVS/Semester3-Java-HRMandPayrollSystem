package Model;

import java.util.ArrayList;
import java.util.List;

public class DepartmentManager {
    private List<Department> departments;

    public DepartmentManager() {
        this.departments = new ArrayList<>();
    }

    public void createDepartment(String name) {
        for (Department department : departments) {
            if (department.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("Department already exists!");
            }
        }
        Department department = new Department(name);
        departments.add(department);
    }

    public List<Department> getDepartments() {
        return new ArrayList<>(departments);
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public void addEmployeeToDepartment(String departmentName, Employee employee) {
        for (Department department : departments) {
            if (department.getName().equals(departmentName)) {
                department.addEmployee(employee);
                break;
            }
        }
    }

    public void deleteDepartment(String departmentName) {
        for (Department department : departments) {
            if (department.getName().equals(departmentName)) {
                // check if department has employees
                if (department.getEmployees().size() > 0) {
                    throw new IllegalArgumentException("Department still has employees!");
                }
                departments.remove(department);
                break;
            }
        }
    }

    public void setNextId(int nextID) {
        Department.setNextID(nextID);
    }
}