package Exceptions;

import Services.LanguageManager;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class LocaleMessages {
    public static final String INVALID_FIELD_BUNDLE = "invalid.field";
    public static final String EMPTY_FIELD_BUNDLE = "empty.field";
    public static final String EMPTY_FIELDS_BUNDLE = "empty.fields";
    public static final String SUCESSFUL_ADD_BUNDLE = "sucessfulAdd.field";
    public static final String GPA = "label.gpa";
    public static final String DEADLINE = "label.deadline";
    public static final String YEAR = "label.year";

    public LocaleMessages(){}


    public static String getLocalizedMessage(String placeholder, String fieldName) {
        LanguageManager languageManager = LanguageManager.getInstance();
        ResourceBundle bundle = languageManager.getResourceBundle();

        String pattern = bundle.getString(placeholder); //merr njeren nga static final exception Strings e cila eshte string ne language properties qe permban nje placeholder
        String fieldNameBundle = bundle.getString(fieldName); //merr njeren nga static final fieldname strings
        System.out.println(MessageFormat.format(pattern, fieldNameBundle));  //TEST FSHIJE
        return MessageFormat.format(pattern, fieldNameBundle);
    }

    public static String getLocalizedMessage(String placeholder) {
        LanguageManager languageManager = LanguageManager.getInstance();
        ResourceBundle bundle = languageManager.getResourceBundle();

        String errorString = bundle.getString(placeholder); //merr njeren nga static final exception Strings e cila eshte string ne language properties qe permban nje placeholder
        System.out.println("Fill all fields");
        return errorString;
    }
}
