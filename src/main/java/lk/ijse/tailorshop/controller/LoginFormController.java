package lk.ijse.tailorshop.controller;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.tailorshop.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {


    @FXML
    private JFXButton btnLogin;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField pw;

    @FXML
    private TextField txtusername;
    private String username="kavi";
    private String password="1234";

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        if (username.equals(txtusername.getText()) && password.equals(pw.getText())){
            navigateToTheDashboard();

        } else {
            new Alert(Alert.AlertType.ERROR,"Incorrect password or Username").show();
        }
    }



    public  void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();

        stage.setTitle("Dashboard Form");
    }


}
