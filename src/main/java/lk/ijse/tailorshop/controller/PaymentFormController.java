package lk.ijse.tailorshop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.tailorshop.db.DbConnection;
import lk.ijse.tailorshop.model.Customer;
import lk.ijse.tailorshop.model.Payment;
import lk.ijse.tailorshop.model.Tm.CustomerTm;
import lk.ijse.tailorshop.model.Tm.PaymentTm;
import lk.ijse.tailorshop.repository.CustomerRepo;
import lk.ijse.tailorshop.repository.OrderRepo;
import lk.ijse.tailorshop.repository.PaymentRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentFormController {
    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnHome;

    @FXML
    private TableColumn<?, ?> colBalance;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colNetTotal;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colPaidAmount;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private Label lblDate;

    @FXML
    private TextField txtPaymentId;


    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TextField txtBalance;

    @FXML
    private TextField txtNetTotal;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtPaidAmount;

    @FXML
    private TextField txtStatus;

    @FXML
    private AnchorPane pane;

    private List<Payment> paymentList = new ArrayList<>();

    public void initialize(){
        this.paymentList = getAllPayments();
        setDate();
        setCellValueFactory();
        loadPaymentTable();
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colNetTotal.setCellValueFactory(new PropertyValueFactory<>("TotalCost"));
        colPaidAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
    }

    private void loadPaymentTable() {
        ObservableList<PaymentTm> tmList = FXCollections.observableArrayList();

        for (Payment payment : paymentList) {
            PaymentTm paymentTm = new PaymentTm(
                    payment.getPaymentId(),
                    payment.getTotalCost(),
                    payment.getAmount(),
                    payment.getBalance(),
                    payment.getStatus(),
                    payment.getDate(),
                    payment.getOrderId()
            );

            tmList.add(paymentTm);
        }
        tblPayment.setItems(tmList);
        PaymentTm selectedItem = tblPayment.getSelectionModel().getSelectedItem();
    }

    private List<Payment> getAllPayments() {
        List<Payment> paymentList = null;
        try {
            paymentList = PaymentRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return paymentList;
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtPaymentId.setText("");
        txtNetTotal.setText("");
        txtPaidAmount.setText("");
        txtBalance.setText("");
        txtStatus.setText("");
        lblDate.setText("");
        txtOrderId.setText("");
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"));
        Stage stage = (Stage) pane.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("Customer Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String orderId = txtOrderId.getText();

        try {
            boolean isDeleted = PaymentRepo.delete(orderId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "payment deleted!").show();
                initialize();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/employee_form.fxml"));
        Stage stage = (Stage) pane.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("Employee Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnExitOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Login Form");
    }

    @FXML
    void btnGarmentOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/garment_form.fxml"));
        Stage stage = (Stage) pane.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("Garment Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) pane.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnMaterialOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/material_form.fxml"));
        Stage stage = (Stage) pane.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("Material Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnMeasurementOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/measurement_form.fxml"));
        Stage stage = (Stage) pane.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("Measurement Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/order_form.fxml"));
        Stage stage = (Stage) pane.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("Order Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String paymentId=txtPaymentId.getText();
        double TotalCost= Double.parseDouble(txtNetTotal.getText());
        double amount= Double.parseDouble(txtPaidAmount.getText());
        double balance= Double.parseDouble(txtBalance.getText());
        String status = txtStatus.getText();
        Date date = Date.valueOf(LocalDate.now());
        String orderId=txtOrderId.getText();

        Payment payment = new Payment(paymentId, TotalCost, amount, balance,status,date,orderId);

        try {
            boolean isSaved = PaymentRepo.save(payment);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "payment saved!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String paymentId=txtPaymentId.getText();
        double TotalCost= Double.parseDouble(txtNetTotal.getText());
        double amount= Double.parseDouble(txtPaidAmount.getText());
        double balance= Double.parseDouble(txtBalance.getText());
        String status = txtStatus.getText();
        Date date = Date.valueOf(LocalDate.now());
        String orderId=txtOrderId.getText();

        Payment payment = new Payment(paymentId, TotalCost, amount, balance,status,date,orderId);

        try {
            boolean isUpdated = PaymentRepo.update(payment);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "payment updated!").show();
                initialize();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    public void txtOrderSearchOnAction(ActionEvent actionEvent) {
        String orderId = txtOrderId.getText();

        try {
            Payment payment = PaymentRepo.searchById(orderId);

            if (payment != null) {
                txtPaymentId.setText(payment.getPaymentId());
                txtNetTotal.setText(String.valueOf(payment.getTotalCost()));
                txtPaidAmount.setText(String.valueOf(payment.getAmount()));
                txtBalance.setText(String.valueOf(payment.getBalance()));
                txtStatus.setText(payment.getStatus());
                lblDate.setText(String.valueOf(payment.getDate()));
                txtOrderId.setText(payment.getOrderId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnGenerateBillOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign =
                JRXmlLoader.load("src/main/resources/Report/garmentBill.jrxml");
        JasperReport jasperReport =
                JasperCompileManager.compileReport(jasperDesign);

        Map<String, Object> data = new HashMap<>();
        data.put("paymentID",txtPaymentId.getText());


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        data,
                        DbConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint,false);
    }

}

