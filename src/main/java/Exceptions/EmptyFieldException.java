package Exceptions;

import javafx.scene.control.Alert;

public class EmptyFieldException extends  RuntimeException{
    public EmptyFieldException(String fieldName){
        super("Fusha "+ fieldName + " eshte e zbrazet!");
        String localeMessage = LocaleMessages.getLocalizedMessage(LocaleMessages.EMPTY_FIELD_BUNDLE, fieldName);
        Alert alert = new Alert(Alert.AlertType.ERROR, localeMessage);
        alert.showAndWait();
    }

    public EmptyFieldException(){
        super("Fill all fields!");
        String localeMessage = LocaleMessages.getLocalizedMessage(LocaleMessages.EMPTY_FIELDS_BUNDLE);
        Alert alert = new Alert(Alert.AlertType.ERROR, localeMessage);
        alert.showAndWait();
    }
}
