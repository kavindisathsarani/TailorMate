package lk.ijse.tailorshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.tailorshop.db.DbConnection;
import lk.ijse.tailorshop.repository.RegisterRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFormController {
    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
     String userId=txtUserId.getText();
     String username=txtUsername.getText();
     String password=txtPassword.getText();

     RegisterRepo.saveUser(userId,username,password);
    }
}
