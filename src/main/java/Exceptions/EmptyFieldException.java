package Exceptions;

import Services.LocaleAlertMessages;
import javafx.scene.control.Alert;
import utils.AlertMessages;

public class EmptyFieldException extends  RuntimeException{
    public EmptyFieldException(String fieldName){
        super("Fusha "+ fieldName + " eshte e zbrazet!");
        String localeMessage = LocaleAlertMessages.getLocalizedMessage(AlertMessages.EMPTY_FIELD_BUNDLE, fieldName);
        Alert alert = new Alert(Alert.AlertType.ERROR, localeMessage);
        alert.showAndWait();
    }

    public EmptyFieldException(){
        super("Fill all fields!");
        String localeMessage = LocaleAlertMessages.getLocalizedMessage(AlertMessages.EMPTY_FIELDS_BUNDLE);
        Alert alert = new Alert(Alert.AlertType.ERROR, localeMessage);
        alert.showAndWait();
    }
}
