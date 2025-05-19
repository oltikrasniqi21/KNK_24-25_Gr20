package controllers;

import Database.DBCustomConnector;
import Services.CurrentUser;
import Services.LanguageManager;
import Services.LoginService;
import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import utils.SceneLocator;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;

import static utils.AlertMessages.showAlert;

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
            Connection conn = DBCustomConnector.getConnection();
            LoginService loginService = new LoginService(conn);

            String role = loginService.authenticate(email, password);

            if (sceneManager == null) {
                sceneManager = SceneManager.getInstance();
            }

            if (role != null) {
                // You may want to fetch the user ID too if it's not a superadmin
                int userId = fetchUserIdByEmail(conn, email);
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

    private int fetchUserIdByEmail(Connection conn, String email) throws Exception {
        String query = "SELECT id FROM users WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1; // For superadmin you might use -1 or 0, as they might not be in DB
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