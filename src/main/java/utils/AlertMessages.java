package utils;

import javafx.scene.control.Alert;

public class AlertMessages {
    public static final String INVALID_FIELD_BUNDLE = "invalid.field";
    public static final String EMPTY_FIELD_BUNDLE = "empty.field";
    public static final String EMPTY_FIELDS_BUNDLE = "empty.fields";
    public static final String SUCCESSFUL_ADD_BUNDLE = "successfulAdd.field";
    public static final String FAILED_ADD_BUNDLE = "unSuccessfulAdd.field";
    public static final String SUCCESSFUL_APPLICATION_BUNDLE = "successfulApplication.field";
    public static final String SOMETHING_WRONG = "wrong.field";
    public static final String SUCCESSFUL_EDIT_BUNDLE = "successfulEdit.field";
    public static final String SELECT_ROW_BUNDLE = "selectRow.field";
    public static final String DELETE_CONFIRMATION = "confirmation.field";
    public static final String ALREADY_EXIST_SCHOLARSHIP = "alreadyExist.Schoalarship";
    public static final String DEACTIVE_CONFIRMATION = "deactive.Schoalarship";
    public static final String INCORRECT_CREDENTIALS = "incorrect.credentials";
    public static final String NO_VALIDATION = "noValidation";

    public static final String GPA = "label.gpa";
    public static final String SCHOLARSHIP = "label.scholarship";
    public static final String DEADLINE = "label.deadline";
    public static final String YEAR = "label.year";
    public static final String MAJOR = "label.major";
    public static final String AMOUNT = "label.amount";
    public static final String NAME = "label.name";
    public static final String EMAIL = "label.email";
    public static final String LAST_NAME = "label.lastName";

    public static final String WEAK_PASSWORD = "weak.password";
    public static final String STRONG_PASSWORD = "strong.password";
    public static final String MATCH = "match.password";
    public static final String NO_MATCH = "noMatch.password";
    public static final String NO_STRONG_PASS_ERROR = "noStrongPassError.password";
    public static final String PASSWORD_HINT = "signup.passwordHint";

    public static final String GPA_VALIDATION = "validation.gpa";




    public static final String INVALID_PASSWORD = "Invalid Password";
    public static final String PASSWORD_REQUIREMENTS = """
            Password must contain:
            • At least 8 characters
            • At least one uppercase letter
            • At least one lowercase letter
            • At least one digit
            • At least one special character (e.g. !@#$%^&*)""";

    public static final String INVALID_EMAIL = "Invalid Email";
    public static final String EMAIL_REQUIREMENTS = "Email must be a valid university student address (e.g. user@student.uni-pr.edu)";

    public static final String MISSING_INFO = "Missing Information";
    public static final String FILL_ALL_FIELDS = "Please fill out all fields.";

    public static final String FILE_UPLOAD_ERROR = "File Upload Error";
    public static final String FILE_SAVE_FAIL = "Could not save your document.";

    public static final String SIGNUP_SUCCESS = "Signup Successful";
    public static final String SIGNUP_SUCCESS_MESSAGE = "Your account has been created.";

    public static final String SELECT_PDF = "Select PDF Document";
    public static final String NO_FILE_SELECTED = "No file selected";

    public static final String ERROR = "Error";

    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
