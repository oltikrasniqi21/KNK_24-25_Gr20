package controllers;

import Services.CreateUserService;
import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.SceneLocator;

public class CreateUsersController {
    @FXML private TextField txtEmri;
    @FXML private TextField txtMbiemri;
    @FXML private TextField txtEmail;
    @FXML private PasswordField pwdPassword;

    private final CreateUserService createUserService;

    public CreateUsersController() {
        this.createUserService = new CreateUserService();
    }

    @FXML
    private void handleShtoUserClick() {
        String firstName = txtEmri.getText();
        String lastName = txtMbiemri.getText();
        String email = txtEmail.getText();
        String password = pwdPassword.getText();

        if (!createUserService.validateFields(firstName, lastName, email, password)) {
            return;
        }

        if (createUserService.createAdminUser(firstName, lastName, email, password)) {
            showAlert("Success", "Admin user added successfully.");
            clearFields();
        } else {
            showAlert("Error", "Failed to add user.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        txtEmri.clear();
        txtMbiemri.clear();
        txtEmail.clear();
        pwdPassword.clear();
    }


}