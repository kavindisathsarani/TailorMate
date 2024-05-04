package lk.ijse.tailorshop.controller;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private TextField txtpassword;

    @FXML
    private TextField txtusername;


    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
    String username=txtusername.getText();
    String password=txtpassword.getText();

     try {
            checkCredential(username, password);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPS! something went wrong").show();
        }
    }

    private void checkCredential(String username, String password) throws SQLException, IOException {
        String sql = "SELECT username, password FROM user WHERE username = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, username);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            String dbPw = resultSet.getString(2);

            if(dbPw.equals(password)) {
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "Password is incorrect!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "username not found!").show();
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

    @FXML
    void linkRegistrationOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/register_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Register Form");

        stage.show();
    }
}
