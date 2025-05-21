package controllers;

import Database.DBCustomConnector;
import Services.CurrentUser;
import Services.LanguageManager;
import Services.LoginService;
import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import utils.SceneLocator;
import javafx.scene.layout.VBox;
import java.util.Locale;

import static utils.AlertMessages.showAlert;

public class LoginController {
    private SceneManager sceneManager;
    private LoginService loginService;
    private final LanguageManager languageManager = LanguageManager.getInstance();

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private VBox rightVBox;

    @FXML
    private HBox languagesHbox;

    @FXML
    private Label lblSignup;

    public LoginController() {

    }

    @FXML
    public void initialize() {
        VBox.setMargin(lblSignup, new Insets(0, 0, 60, 0));
        VBox.setMargin(txtUsername, new Insets(0, 0, 15, 0));
        VBox.setMargin(pwdPassword, new Insets(0, 0, 15, 0));
    }

    @FXML
    private void handleLoginClick() {
        String email = txtUsername.getText();
        String password = pwdPassword.getText();

        try {
            LoginService loginService = new LoginService();
            String role = loginService.authenticate(email, password);

            if (sceneManager == null) {
                sceneManager = SceneManager.getInstance();
            }

            if (role != null) {
                int userId = loginService.fetchUserIdByEmail(email);
                CurrentUser.setUser(userId, role);

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


    public void handleSignupRedirect(MouseEvent mouseEvent) {
        SceneManager.getInstance().loadScene(SceneLocator.SIGNUP_PAGE);
    }
}