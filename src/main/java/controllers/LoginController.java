package controllers;

import Database.DBCustomConnector;
import Services.LanguageManager;
import Services.LoginService;
import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.SceneLocator;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController {
    private SceneManager sceneManager;
    private final LanguageManager languageManager = LanguageManager.getInstance();

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField pwdPassword;

    public LoginController() {

    }

    @FXML
    private void handleLoginClick() {
        String email = txtUsername.getText();
        String password = pwdPassword.getText();

        try {
            LoginService loginService = new LoginService(DBCustomConnector.getConnection());
            String role = loginService.authenticate(email, password);

            if (sceneManager == null) {
                sceneManager = SceneManager.getInstance();
            }

            if (role != null) {
                if (role.equalsIgnoreCase("admin")) {
                    sceneManager.loadScene(SceneLocator.ADMIN_HOME_PAGE);
                } else if (role.equalsIgnoreCase("student")) {
                    sceneManager.loadScene(SceneLocator.STUDENT_HOME_PAGE);
                }
            } else {
                showAlert("Login failed", "Incorrect email or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Database Error!", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    @FXML
    private void handleLoginCancel(){
        SceneManager.getInstance().loadScene(SceneLocator.SIGNUP_PAGE);
    }


    @FXML
    private void handleSQLanguageClick() throws Exception {
        loadLanguage(new Locale("sq"));
    }

    @FXML
    private void handleENLanguageClick() throws Exception {
        loadLanguage(Locale.ENGLISH);
    }

    private void loadLanguage(Locale locale) throws Exception {
        languageManager.setLocale(locale);
        SceneManager.reload();
    }


}