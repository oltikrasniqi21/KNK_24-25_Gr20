package controllers;

import Services.LanguageManager;
import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController {

    private final LanguageManager languageManager = LanguageManager.getInstance();

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField pwdPassword;

    public LoginController(){

    }

    @FXML
    private void handleLoginClick(){

    }

    @FXML
    private void handleLoginCancel(){

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
