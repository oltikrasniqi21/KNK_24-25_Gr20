package Exceptions;

import Services.LanguageManager;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class LocaleMessages {
    public static final String INVALID_FIELD_BUNDLE = "invalid.field";
    public static final String EMPTY_FIELD_BUNDLE = "empty.field";
    public static final String SUCESSFUL_ADD_BUNDLE = "sucessfulAdd.field";
    public static final String GPA = "label.gpa";
    public static final String DEADLINE = "label.deadline";

    public LocaleMessages(){}


    public static String getLocalizedMessage(String staticErrorType, String staticFieldName) {
        LanguageManager languageManager = LanguageManager.getInstance();
        ResourceBundle bundle = languageManager.getResourceBundle();

        String pattern = bundle.getString(staticErrorType); //merr njeren nga static final exception Strings e cila eshte string ne language properties qe permban nje placeholder
        String fieldNameBundle = bundle.getString(staticFieldName); //merr njeren nga static final fieldname strings
        return MessageFormat.format(pattern, fieldNameBundle);
    }
}
