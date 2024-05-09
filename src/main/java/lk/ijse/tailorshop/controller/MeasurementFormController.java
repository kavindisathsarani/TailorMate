package lk.ijse.tailorshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.tailorshop.db.DbConnection;
import lk.ijse.tailorshop.model.Measurement;
import lk.ijse.tailorshop.model.Tm.MeasurementTm;
import lk.ijse.tailorshop.repository.MeasurementRepo;
import lk.ijse.tailorshop.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeasurementFormController {
    @FXML
    private TableColumn<?, ?> colArm;

    @FXML
    private TableColumn<?, ?> colChest;

    @FXML
    private TableColumn<?, ?> colCrotch;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colHip;

    @FXML
    private TableColumn<?, ?> colMeasurementId;

    @FXML
    private TableColumn<?, ?> colNeck;

    @FXML
    private TableColumn<?, ?> colShoulder;

    @FXML
    private TableColumn<?, ?> colSleeve;

    @FXML
    private TableColumn<?, ?> colThigh;

    @FXML
    private TableColumn<?, ?> colTorso;

    @FXML
    private TableColumn<?, ?> colWaist;

    @FXML
    private TableColumn<?, ?> colWaistToHem;

    @FXML
    private TableColumn<?, ?> colWrist;

    @FXML
    private Label lblDate;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<MeasurementTm> tblMeasurement;

    @FXML
    private TextField txtArm;

    @FXML
    private TextField txtChest;

    @FXML
    private TextField txtCrotch;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtHip;

    @FXML
    private TextField txtMeasurementId;

    @FXML
    private TextField txtNeck;

    @FXML
    private TextField txtShoulder;

    @FXML
    private TextField txtSleeve;

    @FXML
    private TextField txtThigh;

    @FXML
    private TextField txtTorso;

    @FXML
    private TextField txtWaist;

    @FXML
    private TextField txtWaistToHem;

    @FXML
    private TextField txtWrist;

    private List<Measurement> measurementList = new ArrayList<>();

    public void initialize() {
        this.measurementList = getAllMeasurements();
        setCellValueFactory();
        loadCustomerTable();
        setDate();

    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    private void loadCustomerTable() {
        ObservableList<MeasurementTm> tmList = FXCollections.observableArrayList();

        for (Measurement measurement : measurementList) {
            MeasurementTm measurementTm = new MeasurementTm(
                    measurement.getMeasurementId(),
                    measurement.getNeckSize(),
                    measurement.getArmhole(),
                    measurement.getSleeveLength(),
                    measurement.getWrist(),
                    measurement.getChest(),
                    measurement.getTorsoLength(),
                    measurement.getWaist(),
                    measurement.getHip(),
                    measurement.getCrotchLength(),
                    measurement.getShoulderLength(),
                    measurement.getThighCircumference(),
                    measurement.getWaistToHem(),
                    measurement.getEmployeeId(),
                    measurement.getCustomerId()
            );

            tmList.add(measurementTm);
        }
        tblMeasurement.setItems(tmList);
        MeasurementTm selectedItem = tblMeasurement.getSelectionModel().getSelectedItem();
    }

    private void setCellValueFactory() {
        colMeasurementId.setCellValueFactory(new PropertyValueFactory<>("measurementId"));
        colNeck.setCellValueFactory(new PropertyValueFactory<>("neckSize"));
        colArm.setCellValueFactory(new PropertyValueFactory<>("armhole"));
        colSleeve.setCellValueFactory(new PropertyValueFactory<>("sleeveLength"));
        colWrist.setCellValueFactory(new PropertyValueFactory<>("wrist"));
        colChest.setCellValueFactory(new PropertyValueFactory<>("chest"));
        colTorso.setCellValueFactory(new PropertyValueFactory<>("torsoLength"));
        colWaist.setCellValueFactory(new PropertyValueFactory<>("waist"));
        colHip.setCellValueFactory(new PropertyValueFactory<>("hip"));
        colCrotch.setCellValueFactory(new PropertyValueFactory<>("crotchLength"));
        colShoulder.setCellValueFactory(new PropertyValueFactory<>("shoulderLength"));
        colThigh.setCellValueFactory(new PropertyValueFactory<>("thighCircumference"));
        colWaistToHem.setCellValueFactory(new PropertyValueFactory<>("waistToHem"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    private List<Measurement> getAllMeasurements() {
        List<Measurement> measurementList = null;
        try {
            measurementList = MeasurementRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return measurementList;
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
        txtCustomerId.setText("");
        txtNeck.setText("");
        txtArm.setText("");
        txtSleeve.setText("");
        txtWrist.setText("");
        txtChest.setText("");
        txtTorso.setText("");
        txtWaist.setText("");
        txtHip.setText("");
        txtCrotch.setText("");
        txtShoulder.setText("");
        txtThigh.setText("");
        txtWaistToHem.setText("");
        txtMeasurementId.setText("");
        txtEmployeeId.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String customerId = txtCustomerId.getText();

        try {
            boolean isDeleted = MeasurementRepo.delete(customerId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "measurement deleted!").show();
                initialize();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Customer Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String measurementId=txtMeasurementId.getText();
        double neckSize=Double.parseDouble(txtNeck.getText());
        double armhole=Double.parseDouble(txtArm.getText());
        double sleeveLength=Double.parseDouble(txtSleeve.getText());
        double wrist=Double.parseDouble(txtWrist.getText());
        double chest=Double.parseDouble(txtChest.getText());
        double torsoLength=Double.parseDouble(txtTorso.getText());
        double waist=Double.parseDouble(txtWaist.getText());
        double hip=Double.parseDouble(txtHip.getText());
        double crotchLength=Double.parseDouble(txtCrotch.getText());
        double shoulderLength=Double.parseDouble(txtShoulder.getText());
        double thighCircumference=Double.parseDouble(txtThigh.getText());
        double waistToHem=Double.parseDouble(txtWaistToHem.getText());
        String employeeId=txtEmployeeId.getText();
        String customerId=txtCustomerId.getText();

        Measurement measurement = new Measurement(measurementId, neckSize, armhole, sleeveLength,wrist,chest,torsoLength,waist,hip,crotchLength,shoulderLength,thighCircumference,waistToHem,employeeId,customerId);

        try {
            boolean isSaved = MeasurementRepo.save(measurement);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "measurement saved!").show();
                initialize();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String measurementId=txtMeasurementId.getText();
        double neckSize=Double.parseDouble(txtNeck.getText());
        double armhole=Double.parseDouble(txtArm.getText());
        double sleeveLength=Double.parseDouble(txtSleeve.getText());
        double wrist=Double.parseDouble(txtWrist.getText());
        double chest=Double.parseDouble(txtChest.getText());
        double torsoLength=Double.parseDouble(txtTorso.getText());
        double waist=Double.parseDouble(txtWaist.getText());
        double hip=Double.parseDouble(txtHip.getText());
        double crotchLength=Double.parseDouble(txtCrotch.getText());
        double shoulderLength=Double.parseDouble(txtShoulder.getText());
        double thighCircumference=Double.parseDouble(txtThigh.getText());
        double waistToHem=Double.parseDouble(txtWaistToHem.getText());
        String employeeId=txtEmployeeId.getText();
        String customerId=txtCustomerId.getText();

        Measurement measurement = new Measurement(measurementId, neckSize, armhole, sleeveLength,wrist,chest,torsoLength,waist,hip,crotchLength,shoulderLength,thighCircumference,waistToHem,employeeId,customerId);

        try {
            boolean isUpdated = MeasurementRepo.update(measurement);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "measurement updated!").show();
                initialize();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String customerId = txtCustomerId.getText();

        try {
            Measurement measurement = MeasurementRepo.searchById(customerId);

            if (measurement != null) {
                txtCustomerId.setText(measurement.getCustomerId());
                txtNeck.setText(String.valueOf(measurement.getNeckSize()));
                txtArm.setText(String.valueOf(measurement.getArmhole()));
                txtSleeve.setText(String.valueOf(measurement.getSleeveLength()));
                txtWrist.setText(String.valueOf(measurement.getWrist()));
                txtChest.setText(String.valueOf(measurement.getChest()));
                txtTorso.setText(String.valueOf(measurement.getTorsoLength()));
                txtWaist.setText(String.valueOf(measurement.getWaist()));
                txtHip.setText(String.valueOf(measurement.getHip()));
                txtCrotch.setText(String.valueOf(measurement.getCrotchLength()));
                txtShoulder.setText(String.valueOf(measurement.getShoulderLength()));
                txtThigh.setText(String.valueOf(measurement.getThighCircumference()));
                txtWaistToHem.setText(String.valueOf(measurement.getWaistToHem()));
                txtMeasurementId.setText(measurement.getMeasurementId());
                txtEmployeeId.setText(measurement.getEmployeeId());

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void txtMeasurementIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.tailorshop.util.TextField.MEASUREMENTID,txtMeasurementId);

    }

    public void txtCustomerIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.tailorshop.util.TextField.CUSTOMERID,txtCustomerId);

    }

    public void txtEmployeeIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.tailorshop.util.TextField.EMPLOYEEID,txtEmployeeId);

    }

    public void btnGenerateReportOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign =
                JRXmlLoader.load("src/main/resources/Report/MeasurementReport.jrxml");
        JasperReport jasperReport =
                JasperCompileManager.compileReport(jasperDesign);

        Map<String, Object> data = new HashMap<>();
        data.put("CustomerID",txtCustomerId.getText());


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        data,
                        DbConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint,false);
    }

}

