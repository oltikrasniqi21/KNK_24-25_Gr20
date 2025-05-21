package Services;

import javafx.scene.control.Alert;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class LocaleAlertMessages {


    public LocaleAlertMessages(){}

    public static String getLocalizedMessage(String placeholder, String fieldName) {
        LanguageManager languageManager = LanguageManager.getInstance();
        ResourceBundle bundle = languageManager.getResourceBundle();

        String pattern = bundle.getString(placeholder);
        String fieldNameBundle = bundle.getString(fieldName);
        return MessageFormat.format(pattern, fieldNameBundle);
    }

    public static String getLocalizedMessage(String message) {
        LanguageManager languageManager = LanguageManager.getInstance();
        ResourceBundle bundle = languageManager.getResourceBundle();

        String messageString = bundle.getString(message);
        return messageString;
    }

    public static void showErrorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, getLocalizedMessage(message));
        alert.showAndWait();
    }
    public static void showInformationAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, getLocalizedMessage(message));
        alert.showAndWait();
    }
}
