package controllers;

import Database.DBCustomConnector;
import Services.LanguageManager;
import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.SceneLocator;

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

    public LoginController(){

    }

    @FXML
    private void handleLoginClick(){
        String email = txtUsername.getText();
        String password = pwdPassword.getText();

        try{
            Connection conn = DBCustomConnector.getConnection();
            String query = "SELECT role FROM users WHERE email = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,email);
            statement.setString(2,password);

            ResultSet rs = statement.executeQuery();

            if(sceneManager == null){
                sceneManager = SceneManager.getInstance();
            }

            if(rs.next()){
                String role = rs.getString("role");

                if(role.equalsIgnoreCase("admin")){
                    sceneManager.loadScene(SceneLocator.ADMIN_HOME_PAGE);
                }else if(role.equalsIgnoreCase("student")){
                    sceneManager.loadScene(SceneLocator.STUDENT_HOME_PAGE);
                }
            }else
            {
                showAlert("Login failed", "Incorrect email or password");
            }
        }catch (Exception e){
            e.printStackTrace();
            showAlert("Databases Error!", e.getMessage());
        }
    }

    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    @FXML
    private void handleLoginCancel(){
        txtUsername.clear();
        pwdPassword.clear();
    }

    @FXML
    private void handleSQLanguageClick() throws Exception{
        loadLanguage(new Locale("sq"));
    }

    @FXML
    private void handleENLanguageClick() throws Exception{
        loadLanguage(Locale.ENGLISH);
    }

    private void loadLanguage(Locale locale) throws Exception{
        languageManager.setLocale(locale);
        SceneManager.reload();
    }

}
