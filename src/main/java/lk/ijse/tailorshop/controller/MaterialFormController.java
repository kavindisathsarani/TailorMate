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
import lk.ijse.tailorshop.model.Material;
import lk.ijse.tailorshop.model.Tm.MaterialTm;
import lk.ijse.tailorshop.repository.MaterialRepo;
import lk.ijse.tailorshop.util.Regex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MaterialFormController {
    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colMaterialId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<MaterialTm> tblMaterial;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtMaterialId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    private List<Material> materialList = new ArrayList<>();

    public void initialize() {
        this.materialList = getAllCustomers();
        setCellValueFactory();
        loadCustomerTable();
    }

    private void loadCustomerTable() {
        ObservableList<MaterialTm> tmList = FXCollections.observableArrayList();

        for (Material material : materialList) {
            MaterialTm materialTm = new MaterialTm(
                    material.getMaterialId(),
                    material.getDescription(),
                    material.getQty(),
                    material.getUnitPrice(),
                    material.getCustomerId()
            );

            tmList.add(materialTm);
        }
        tblMaterial.setItems(tmList);
        MaterialTm selectedItem = tblMaterial.getSelectionModel().getSelectedItem();
    }

    private void setCellValueFactory() {
        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }

    private List<Material> getAllCustomers() {
        List<Material> materialList = null;
        try {
            materialList = MaterialRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return materialList;
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
        txtMaterialId.setText("");
        txtDescription.setText("");
        txtQty.setText("");
        txtUnitPrice.setText("");
        txtCustomerId.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String materialId = txtMaterialId.getText();

        try {
            boolean isDeleted = MaterialRepo.delete(materialId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "material deleted!").show();
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
        String materialId=txtMaterialId.getText();
        String description=txtDescription.getText();
        double qty= Double.parseDouble(txtQty.getText());
        double unitPrice= Double.parseDouble(txtUnitPrice.getText());
        String customerId=txtCustomerId.getText();

        Material material = new Material(materialId, description, qty, unitPrice,customerId);

        try {
            boolean isSaved = MaterialRepo.save(material);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "material saved!").show();
                initialize();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String materialId=txtMaterialId.getText();
        String description=txtDescription.getText();
        double qty= Double.parseDouble(txtQty.getText());
        double unitPrice= Double.parseDouble(txtUnitPrice.getText());
        String customerId=txtCustomerId.getText();

        Material material = new Material(materialId, description, qty, unitPrice,customerId);

        try {
            boolean isUpdated = MaterialRepo.update(material);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "materail updated!").show();
                initialize();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String materialId = txtMaterialId.getText();

        try {
            Material material = MaterialRepo.searchById(materialId);

            if (material != null) {
                txtMaterialId.setText(material.getMaterialId());
                txtDescription.setText(material.getDescription());
                txtQty.setText(String.valueOf(material.getQty()));
                txtUnitPrice.setText(String.valueOf(material.getUnitPrice()));
                txtCustomerId.setText(material.getCustomerId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void txtMaterialIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.tailorshop.util.TextField.MATERIALID,txtMaterialId);

    }


    public void txtCustomerIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.tailorshop.util.TextField.CUSTOMERID,txtCustomerId);

    }

    public void txtQtyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.tailorshop.util.TextField.QTY,txtQty);

    }
}
