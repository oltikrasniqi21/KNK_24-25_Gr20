package controllers;

import Services.CreateUserService;
import Services.LocaleAlertMessages;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.AlertMessages;

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
            LocaleAlertMessages.showInformationAlert(AlertMessages.SUCCESSFUL_ADD_BUNDLE);
            clearFields();
        } else {
            LocaleAlertMessages.showErrorAlert(AlertMessages.FAILED_ADD_BUNDLE);
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