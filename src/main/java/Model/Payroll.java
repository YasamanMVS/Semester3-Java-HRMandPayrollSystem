package Model;
import java.io.Serializable;

public class Payroll implements Serializable{
    // serialVersionUID field
    private static final long serialVersionUID = 1L;

    private static int nextID = 1;
    private int id;
    private int employeeID;
    private double basePay; // Regular hourly wage
    private double hoursWorked;
    private double overtime; // Hours worked beyond the standard
    private double bonus; // Additional earnings

    // Constructor
    public Payroll(int employeeID, double basePay, double hoursWorked, double overtime, double bonus) {
        this.id = nextID;
        nextID++;
        this.employeeID = employeeID;
        this.basePay = basePay;
        this.hoursWorked = hoursWorked;
        this.overtime = overtime;
        this.bonus = bonus;
    }

    // Getter and Setters
    public int getId() {
        return employeeID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        if (employeeID <= 0) {
            throw new IllegalArgumentException("Employee ID must be a positive number!!");
        }
        this.employeeID = employeeID;
    }

    public double getBasePay() {
        return basePay;
    }

    public void setBasePay(double basePay) {
        if (basePay < 0) {
            throw new IllegalArgumentException("Base pay cannot be negative!!");
        }
        this.basePay = basePay;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        if (hoursWorked < 0) {
            throw new IllegalArgumentException("Hours worked cannot be negative!!");
        }
        this.hoursWorked = hoursWorked;
    }

    public double getOvertime() {
        return overtime;
    }

    public void setOvertime(double overtime) {
        if (overtime < 0) {
            throw new IllegalArgumentException("Overtime cannot be negative!!");
        }
        this.overtime = overtime;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        if (bonus < 0) {
            throw new IllegalArgumentException("Bonus cannot be negative");
        }
        this.bonus = bonus;
    }

    public static void setNextId(int nextID) {
        Payroll.nextID = nextID;
    }

    // Payroll methods
    public double calculateGrossPay() {
        // Assuming overtime pay is 1.5 times the base pay rate
        return (hoursWorked * basePay) + (overtime * basePay * 1.5) + bonus;
    }
    public double calculateNetPay() {
        double taxRate = 0.13;
        double grossPay = calculateGrossPay();
        return grossPay - (grossPay * taxRate);
    }
}
