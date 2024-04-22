package Model;

import java.util.HashMap;
import java.util.Map;

public class PayrollManager {

    // A map to hold payroll records for each employee, keyed by employee ID.
    private Map<Integer, Payroll> payrollRecords;

    // Constructor to initialize the payrollRecords map.
    public PayrollManager() {
        this.payrollRecords = new HashMap<>();
    }

    // Processes payroll for a given employee.
    public void processPayrollForEmployee(Employee employee, double hoursWorked, double overtimeHours, double bonus) {
        // Create a new Payroll object for the employee.
        Payroll payroll = payrollRecords.getOrDefault(employee.getId(),
                new Payroll(employee.getId(), 0, 0, 0, 0));

        // Update the Payroll
        payroll.setBasePay(employee.getSalary());
        payroll.setHoursWorked(hoursWorked);
        payroll.setOvertime(overtimeHours);
        payroll.setBonus(bonus);

        // Store the updated payroll information.
        payrollRecords.put(employee.getId(), payroll);
    }

    public Map<Integer, Payroll> getPayrolls() {
        return payrollRecords;
    }

    public void setPayrolls(Map<Integer, Payroll> payrolls) {
        this.payrollRecords = payrolls;
    }

    // Retrieves the payroll information for a specific employee by their ID.
    public Payroll getPayrollForEmployee(int employeeID) {
        return payrollRecords.get(employeeID);
    }

    public void setNextId(int nextID) {
        Payroll.setNextId(nextID);
    }
}
