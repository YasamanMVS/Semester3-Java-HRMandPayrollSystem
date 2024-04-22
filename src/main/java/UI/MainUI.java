// Group Members with Student IDs :

//  Yasaman Mirvahabi Sabet        101217770
//  Dorsa Mohammadi                101397591
//  Thanh Vu Le                    101411302

package UI;

import Model.*;
import Ulti.DataManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MainUI extends Application {
    private TableView<Employee> employeeTable;
    private TextField firstNameField, lastNameField, departmentField, positionField, salaryField;
    private EmployeeManager employeeManager = new EmployeeManager();
    private PayrollManager payrollManager = new PayrollManager();
    private Label errorMessage = new Label();
    private AuthenticationManager authenticationManager = new AuthenticationManager();
    private ChoiceBox<String> departmentChoiceBox = new ChoiceBox<>();
    private DepartmentManager departmentsManager = new DepartmentManager();

    @Override
    public void init() throws Exception {
        super.init();
        if (Files.exists(Paths.get("employees.dat"))) {
            List<Employee> employees = DataManager.loadEmployeeData();
            employeeManager.setEmployees(employees);
            int maxEmployeeId = employees.stream().mapToInt(Employee::getId).max().orElse(0);
            employeeManager.setNextId(maxEmployeeId + 1);
        }
        
        if (Files.exists(Paths.get("departments.dat"))) {
            List<Department> departments = DataManager.loadDepartmentData();
            departmentsManager.setDepartments(departments);
            int maxDepartmentId = departments.stream().mapToInt(Department::getDepartmentID).max().orElse(0);
            departmentsManager.setNextId(maxDepartmentId + 1);
        }
        
        if (Files.exists(Paths.get("payrolls.dat"))) {
            List<Payroll> payrolls = DataManager.loadPayrollData();
            Map<Integer, Payroll> payrollsMap = new HashMap<>();
            for (Payroll payroll : payrolls) {
                payrollsMap.put(payroll.getId(), payroll);
            }
            payrollManager.setPayrolls(payrollsMap);
            int maxPayrollId = payrolls.stream().mapToInt(Payroll::getId).max().orElse(0);
            payrollManager.setNextId(maxPayrollId + 1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        showLoginWindow(primaryStage);
    }

    public void showLoginWindow(Stage primaryStage) {
        Stage loginWindow = new Stage();
    
        // Create layout
        VBox layout = new VBox(10);
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Label errorMessage = new Label();
        Button loginButton = new Button("Login");
        layout.getChildren().addAll(new Label("Username:"), usernameField, new Label("Password:"), passwordField, loginButton, errorMessage);
    
        // Set action for login button
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (authenticationManager.authenticate(username, password)) {
                // Authentication successful
                loginWindow.close();
                // Show admin window if user is admin
                if (authenticationManager.getUserRole(username) == User.UserRole.HR) {
                    showAdminWindow(primaryStage);
                    return;
                } else {
                    // Show main window
                    showMainWindow(primaryStage);
                }
            } else {
                // Authentication failed
                errorMessage.setText("Invalid username or password.");
            }
        });
    
        // Set the scene with the layout
        Scene scene = new Scene(layout, 300, 200);
        layout.setPadding(new Insets(20));
        loginWindow.setScene(scene);
        loginWindow.show();
    }

    public void showAdminWindow(Stage primaryStage) {
        // Create buttons
        Button addDepartmentButton = new Button("Add Department");
        Button deleteDepartmentButton = new Button("Delete Department");
        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-text-fill: red");
        logoutButton.setOnAction(e -> {
            primaryStage.close();
            showLoginWindow(primaryStage);
        });
        Label errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red");
        // show table of departments
        TableView<Department> departmentTable = new TableView<>();
        departmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Department, String> departmentNameCol = new TableColumn<>("Department Name");
        departmentNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        departmentTable.getColumns().add(departmentNameCol);
        departmentTable.getItems().addAll(departmentsManager.getDepartments());
        
        // Set actions for buttons
        addDepartmentButton.setOnAction(e -> {
            // Create a dialog to enter the department name
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Department");
            dialog.setHeaderText("Enter the department name:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                try {
                    // Add the department to the existing departmentsManager
                    departmentsManager.createDepartment(name);
                    DataManager.saveDepartmentData(departmentsManager.getDepartments());
                    // Show success message
                    errorMessage.setText("Department added successfully!");
                    errorMessage.setStyle("-fx-text-fill: green");
                    // Add the department to the table
                    departmentTable.getItems().add(departmentsManager.getDepartments().get(departmentsManager.getDepartments().size() - 1));
                } catch (IOException ex) {
                    // show error message
                    errorMessage.setText("Error saving department data.");
                } catch (IllegalArgumentException ex) {
                    // show error message
                    errorMessage.setText(ex.getMessage());
                }
            });
        });     
        deleteDepartmentButton.setOnAction(e -> {
            Department selectedDepartment = departmentTable.getSelectionModel().getSelectedItem();
            if (selectedDepartment != null) {
                if (!selectedDepartment.getEmployees().isEmpty()) {
                    errorMessage.setText("Cannot delete department with employees.");
                    errorMessage.setStyle("-fx-text-fill: red");
                } else {
                    departmentsManager.deleteDepartment(selectedDepartment.getName());
                    departmentTable.getItems().remove(selectedDepartment);
                    try {
                        DataManager.saveDepartmentData(departmentsManager.getDepartments());
                        errorMessage.setText("Department deleted successfully!");
                        errorMessage.setStyle("-fx-text-fill: green");
                    } catch (IOException ex) {
                        errorMessage.setText("Error saving department data.");
                    }
                }
            }
        });
        // Add buttons to layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(addDepartmentButton, deleteDepartmentButton, errorMessage, departmentTable, logoutButton);
    
        // Create a scene and show the window
        Scene scene = new Scene(layout, 300, 400);
        layout.setPadding(new Insets(20));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showMainWindow(Stage primaryStage) {
        employeeTable = new TableView<>();
        // Create TableView to display employees
        employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Employee, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        TableColumn<Employee, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        TableColumn<Employee, String> departmentCol = new TableColumn<>("Department");
        departmentCol.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        departmentCol.setCellFactory(ComboBoxTableCell.forTableColumn(departmentChoiceBox.getItems()));
        TableColumn<Employee, String> positionCol = new TableColumn<>("Position");
        positionCol.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
        TableColumn<Employee, Double> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(cellData -> cellData.getValue().salaryProperty().asObject());

        // Populate departments choice box
        for (Department department : departmentsManager.getDepartments()) {
            departmentChoiceBox.getItems().add(department.getName());
        }

        // Populate table with employees
        employeeTable.getItems().addAll(employeeManager.getEmployees());
        employeeTable.getColumns().addAll(firstNameCol, lastNameCol, departmentCol, positionCol, salaryCol);

        // Create form to add/edit employee details
        Label firstNameLabel = new Label("First Name:");
        Label lastNameLabel = new Label("Last Name:");
        Label departmentLabel = new Label("Department:");
        Label positionLabel = new Label("Position:");
        Label salaryLabel = new Label("Salary:");

        firstNameField = new TextField();
        lastNameField = new TextField();
        positionField = new TextField();
        salaryField = new TextField();

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || 
                departmentChoiceBox.getValue() == null || positionField.getText().isEmpty() || 
                salaryField.getText().isEmpty()) {
                errorMessage("All fields are required.");
            } else {
                addEmployee();
            }
        });

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateEmployee());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteEmployee());

        // error message
        errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red");

        // Process payroll button
        Button processPayrollButton = new Button("Process Payroll");
        processPayrollButton.setOnAction(e -> {
            Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
            if (selectedEmployee == null) {
                errorMessage("No employee selected.");
                return;
            }

            // Create a new window (Stage)
            Stage payrollWindow = new Stage();

            // Create layout
            VBox layout = new VBox(10);
            TextField hoursWorkedField = new TextField();
            TextField overtimeHoursField = new TextField();
            TextField bonusField = new TextField();
            Button submitButton = new Button("Submit");
            layout.getChildren().addAll(
                new Label("Hours Worked:"), 
                hoursWorkedField, new Label("Overtime Hours:"), 
                overtimeHoursField, new Label("Bonus:"), 
                bonusField, submitButton
            );

            // Set action for submit button
            submitButton.setOnAction(ev -> {
                double hoursWorked = Double.parseDouble(hoursWorkedField.getText());
                double overtimeHours = Double.parseDouble(overtimeHoursField.getText());
                double bonus = Double.parseDouble(bonusField.getText());
                payrollManager.processPayrollForEmployee(selectedEmployee, hoursWorked, overtimeHours, bonus);
                payrollWindow.close();
            });

            // Set the scene with the layout
            Scene scene = new Scene(layout, 300, 250);
            payrollWindow.setScene(scene);
            payrollWindow.show();
        });

        // Payroll report button
        Button payrollReportButton = new Button("Payroll Report");
        payrollReportButton.setOnAction(e -> {
            Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
            if (selectedEmployee == null) {
                errorMessage("No employee selected.");
                return;
            }

            Payroll payroll = payrollManager.getPayrollForEmployee(selectedEmployee.getId());
            if (payroll == null) {
                errorMessage("No payroll for selected employee.");
                return;
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Payroll Report");
            alert.setHeaderText("Payroll for " + selectedEmployee.getFirstName() + " " + selectedEmployee.getLastName());
            alert.setContentText("Base Pay: " + payroll.getBasePay() + "\nHours Worked: " + payroll.getHoursWorked() + "\nOvertime: " + payroll.getOvertime() + "\nBonus: " + payroll.getBonus() + "\nGross Pay: " + payroll.calculateGrossPay() + "\nNet Pay: " + payroll.calculateNetPay());
            alert.showAndWait();
        });

        employeeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Employee selectedEmployee = newSelection;
                // insert the selected employee's details into the form
                firstNameField.setText(selectedEmployee.getFirstName());
                lastNameField.setText(selectedEmployee.getLastName());
                departmentChoiceBox.setValue(selectedEmployee.getDepartment());
                positionField.setText(selectedEmployee.getPosition());
                salaryField.setText(String.valueOf(selectedEmployee.getSalary()));
            }
        });
        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-text-fill: red");
        logoutButton.setOnAction(e -> {
            primaryStage.close();
            showLoginWindow(primaryStage);
        });

        VBox buttonLayout = new VBox(10);
        buttonLayout.getChildren().addAll(payrollReportButton, processPayrollButton);  
        // Create a new BorderPane
        BorderPane root2 = new BorderPane();
        root2.setCenter(buttonLayout);
        root2.setBottom(logoutButton);  

        VBox formLayout = new VBox(10);
        formLayout.getChildren().addAll(
                firstNameLabel, firstNameField,
                lastNameLabel, lastNameField,
                departmentLabel, departmentChoiceBox,
                positionLabel, positionField,
                salaryLabel, salaryField,
                addButton, updateButton, deleteButton,
                errorMessage
        );

        // Layout setup
        HBox root = new HBox(20);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(formLayout, employeeTable, root2);

        Scene scene = new Scene(root, 800, 500);
        primaryStage.setTitle("Employee Management App");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            try {
                DataManager.saveEmployeeData(employeeManager.getEmployees());
                DataManager.saveDepartmentData(departmentsManager.getDepartments());
                DataManager.savePayrollData(new ArrayList<>(payrollManager.getPayrolls().values()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void addEmployee() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String department = departmentChoiceBox.getValue();
        String position = positionField.getText();
        String salary = salaryField.getText();
        // Create new employee
        try {
            Employee newEmployee = employeeManager.addEmployee(firstName, lastName, department, position, salary);
            departmentsManager.addEmployeeToDepartment(department, newEmployee);
            employeeTable.getItems().add(newEmployee);
        } catch (IllegalArgumentException e) {
            // Show error message
            errorMessage(e.getMessage());
            return;
        }
        clearFields();
        successMessage("Employee added successfully!");
    }

   private void deleteEmployee() {
       Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
       if (selectedEmployee != null) {
            employeeTable.getItems().remove(selectedEmployee);
            employeeManager.deleteEmployee(selectedEmployee);
            clearFields();
            successMessage("Employee deleted successfully!");
       }
   }

   private void updateEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String department = departmentChoiceBox.getValue();
            String position = positionField.getText();
            String salary = salaryField.getText();
            // Create new employee
            try {
                Employee updatedEmployee = new Employee(firstName, lastName, department, position, Double.parseDouble(salary));
                updatedEmployee.setNextId(selectedEmployee.getId());
                employeeManager.updateEmployee(updatedEmployee);
                employeeTable.getItems().set(employeeTable.getSelectionModel().getSelectedIndex(), updatedEmployee);
            } catch (IllegalArgumentException e) {
                // Show error message
                errorMessage(e.getMessage());
            return;
            }
            clearFields();
            successMessage("Employee updated successfully!");
        }

   }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        departmentChoiceBox.setValue(null);
        positionField.clear();
        salaryField.clear();
    }

    private void successMessage(String message) {
        errorMessage.setText(message);
        errorMessage.setStyle("-fx-text-fill: green");
    }

    private void errorMessage(String message) {
        errorMessage.setText(message);
        errorMessage.setStyle("-fx-text-fill: red");
    }

}