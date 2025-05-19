package Services;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class LocaleAlertMessages {


    public LocaleAlertMessages(){}

    public static String getLocalizedMessage(String placeholder, String fieldName) {
        LanguageManager languageManager = LanguageManager.getInstance();
        ResourceBundle bundle = languageManager.getResourceBundle();

        String pattern = bundle.getString(placeholder); //merr njeren nga static final exception Strings nga language.propertiesBundle qe permban nje placeholder
        String fieldNameBundle = bundle.getString(fieldName); //merr njeren nga static final fieldname strings
        return MessageFormat.format(pattern, fieldNameBundle); //e vendos fieldname ne placeholder
    }

    public static String getLocalizedMessage(String message) {
        LanguageManager languageManager = LanguageManager.getInstance();
        ResourceBundle bundle = languageManager.getResourceBundle();

        String messageString = bundle.getString(message); //merr njeren nga static final Strings ne language properties qe permban nje placeholder
        return messageString;
    }
}
