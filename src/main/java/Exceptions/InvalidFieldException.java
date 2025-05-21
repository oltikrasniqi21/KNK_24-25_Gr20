package Exceptions;

import Services.LocaleAlertMessages;
import javafx.scene.control.Alert;
import utils.AlertMessages;

public class InvalidFieldException extends RuntimeException{
    public InvalidFieldException(String fieldName){
        super("Fusha "+fieldName+" nuk validohet!");
        String localeMessage = LocaleAlertMessages.getLocalizedMessage(AlertMessages.INVALID_FIELD_BUNDLE,fieldName);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(localeMessage);
        alert.setContentText(null);
        alert.showAndWait();
    }
}
