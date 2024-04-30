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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.tailorshop.model.Customer;
import lk.ijse.tailorshop.model.Tm.CustomerTm;
import lk.ijse.tailorshop.repository.CustomerRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerFormController {
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNumber;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private AnchorPane root;

    private List<Customer> customerList = new ArrayList<>();

    public void initialize() {
        this.customerList = getAllCustomers();
        setCellValueFactory();
        loadCustomerTable();
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();

        for (Customer customer : customerList) {
            CustomerTm customerTm = new CustomerTm(
                    customer.getCustomerId(),
                    customer.getName(),
                    customer.getGender(),
                    customer.getAddress(),
                    customer.getContactNumber(),
                    customer.getEmail()
            );

            tmList.add(customerTm);
        }
        tblCustomer.setItems(tmList);
        CustomerTm selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
    }

    private void setCellValueFactory() {
    colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
    colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private List<Customer> getAllCustomers() {

        List<Customer> customerList = null;
        try {
            customerList = CustomerRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
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
        txtGender.setText("");
        txtAddress.setText("");
        txtContactNumber.setText(String.valueOf(""));
        txtEmail.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String customerId = txtId.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(customerId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
     String customerId=txtId.getText();
     String name=txtName.getText();
     String gender=txtGender.getText();
     String address=txtAddress.getText();
     int contactNumber=Integer.parseInt(txtContactNumber.getText());
     String email=txtEmail.getText();

        Customer customer = new Customer(customerId, name, gender, address,contactNumber,email);

        try {
            boolean isSaved = CustomerRepo.save(customer);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String customerId =txtId.getText();
        String name=txtName.getText();
        String gender=txtGender.getText();
        String address=txtAddress.getText();
        int contactNumber=Integer.parseInt(txtContactNumber.getText());
        String email=txtEmail.getText();

        Customer customer = new Customer(customerId, name, gender, address,contactNumber,email);

        try {
            boolean isUpdated = CustomerRepo.update(customer);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String customerId = txtId.getText();

        try {
            Customer customer = CustomerRepo.searchById(customerId);

            if (customer != null) {
                txtId.setText(customer.getCustomerId());
                txtName.setText(customer.getName());
                txtGender.setText(customer.getGender());
                txtAddress.setText(customer.getAddress());
                txtContactNumber.setText(String.valueOf(customer.getContactNumber()));
                txtEmail.setText(customer.getEmail());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
