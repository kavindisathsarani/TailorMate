package lk.ijse.tailorshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.tailorshop.repository.RegisterRepo;

import java.io.IOException;

public class RegisterFormController {
    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUsername;

    @FXML
    private AnchorPane rootNode;

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        RegisterRepo.saveUser(userId, username, password);
    }

    public void linkBackToLoginOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setResizable(false);
        stage.setTitle("login Form");
        stage.centerOnScreen();
    }

}