package Exceptions;

import javafx.scene.control.Alert;

public class InvalidFieldException extends RuntimeException{
    public InvalidFieldException(String fieldName){
        super("Fusha "+fieldName+" nuk validohet!");
        String localeMessage = LocaleMessages.getLocalizedMessage(LocaleMessages.INVALID_FIELD_BUNDLE,fieldName);
        Alert alert = new Alert(Alert.AlertType.ERROR, localeMessage );
        alert.showAndWait();
    }
}
