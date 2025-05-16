package Exceptions;

import javafx.scene.control.Alert;

public class EmptyFieldException extends  RuntimeException{
    public EmptyFieldException(String fieldName){
        super("Fusha "+ fieldName + " eshte e zbrazet!");
        String localeMessage = LocaleMessages.getLocalizedMessage(LocaleMessages.EMPTY_FIELD_BUNDLE, fieldName);
        Alert alert = new Alert(Alert.AlertType.ERROR, localeMessage);
    }
}
