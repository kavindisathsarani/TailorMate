package lk.ijse.tailorshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.tailorshop.model.Employee;
import lk.ijse.tailorshop.model.Tm.EmployeeTm;
import lk.ijse.tailorshop.repository.EmployeeRepo;
import lk.ijse.tailorshop.util.Regex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFormController {
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNumber;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPosition;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPosition;

    private List<Employee> employeeList = new ArrayList<>();

    public void initialize() {
        this.employeeList = getAllEmployees();
        setCellValueFactory();
        loadEmployeeTable();
    }

    private void loadEmployeeTable() {
        ObservableList<EmployeeTm> tmList = FXCollections.observableArrayList();

        for (Employee employee : employeeList) {
            EmployeeTm employeeTm = new EmployeeTm(
                    employee.getEmployeeId(),
                    employee.getName(),
                    employee.getAddress(),
                    employee.getContactNumber(),
                    employee.getPosition()
            );

            tmList.add(employeeTm);
        }
        tblEmployee.setItems(tmList);
        EmployeeTm selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));

    }

    private List<Employee> getAllEmployees() {
        List<Employee> employeeList = null;
        try {
            employeeList = EmployeeRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContactNumber.setText(String.valueOf(""));
        txtPosition.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String empoyeeId = txtId.getText();

        try {
            boolean isDeleted = EmployeeRepo.delete(empoyeeId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted!").show();
                initialize();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if(txtId.getText().isEmpty() || txtAddress.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill in empty fields before adding a new employee!").show();

        }else {
            String employeeId = txtId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            int contactNumber = Integer.parseInt(txtContactNumber.getText());
            String position = txtPosition.getText();

            Employee employee = new Employee(employeeId, name, address, contactNumber, position);

            if (isValid()) {
                try {
                    boolean isSaved = EmployeeRepo.save(employee);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!").show();
                        initialize();

                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Oops! It seems there are errors in the fields you filled. Please review and correct the information accordingly!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String employeeId=txtId.getText();
        String name=txtName.getText();
        String address=txtAddress.getText();
        int contactNumber=Integer.parseInt(txtContactNumber.getText());
        String position=txtPosition.getText();

        Employee employee = new Employee(employeeId,name,address,contactNumber,position);

     if(isValid()) {
         try {
             boolean isUpdated = EmployeeRepo.update(employee);
             if (isUpdated) {
                 new Alert(Alert.AlertType.CONFIRMATION, "Employee updated!").show();
                 initialize();

             }
         } catch (SQLException e) {
             new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
         }
     }else {
         new Alert(Alert.AlertType.ERROR, "Oops! It seems there are errors in the fields you filled. Please review and correct the information accordingly!").show();
       }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String employeeId = txtId.getText();

        try {
            Employee employee = EmployeeRepo.searchById(employeeId);

            if (employee != null) {
                txtId.setText(employee.getEmployeeId());
                txtName.setText(employee.getName());
                txtAddress.setText(employee.getAddress());
                txtContactNumber.setText(String.valueOf(employee.getContactNumber()));
                txtPosition.setText(employee.getPosition());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void txtEmployeeIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.tailorshop.util.TextField.EMPLOYEEID,txtId);

    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.tailorshop.util.TextField.ADDRESS,txtAddress);

    }

    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.tailorshop.util.TextField.EMPLOYEEID,txtId)) return false;
        if (!Regex.setTextColor(lk.ijse.tailorshop.util.TextField.ADDRESS,txtAddress)) return false;
        return true;
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/employee_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("Employee Form");
        stage.centerOnScreen();
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("Customer Form");
        stage.centerOnScreen();
    }

    public void btnMeasurementOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/measurement_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("Measurement Form");
        stage.centerOnScreen();
    }

    public void btnMaterialOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/material_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("Material Form");
        stage.centerOnScreen();
    }

    public void btnGarmentOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/garment_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("Garment Form");
        stage.centerOnScreen();
    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/order_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("Order Form");
        stage.centerOnScreen();
    }

    public void btnExitOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Login Form");
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

}

