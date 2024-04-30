package lk.ijse.tailorshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.tailorshop.db.DbConnection;

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

     saveUser(userId,username,password);
    }

    private void saveUser(String userId, String username, String password) {
        try {
            String sql = "INSERT INTO user VALUES(?, ?, ?)";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1, userId);
            pstm.setObject(2, username);
            pstm.setObject(3, password);

            if(pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happend!").show();
        }
    }

}
