package controllers;

import Services.SceneManager;
import Services.UniversityService;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import Services.SignupService;
import utils.SceneLocator;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

import static utils.AlertMessages.*;

public class SignupController {
    private final UniversityService universityService;

    public SignupController() {
        this.universityService = new UniversityService();
        this.signupService = new SignupService();
    }

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label lblSelectedFile;
    @FXML
    private Label passwordHintLabel;
    @FXML
    private VBox titleVbox;
    @FXML
    private VBox buttonVbox1;
    @FXML
    private VBox buttonVbox2;
    @FXML
    private ComboBox<String> universityComboBox;
    @FXML
    private ComboBox<String> facultyComboBox;
    @FXML
    private ComboBox<String> majorComboBox;
    @FXML
    private ComboBox<String> yearComboBox;

    private SignupService signupService = new SignupService();

    private File selectedPdfFile;

    @FXML
    public void initialize() {
        universityService.loadUniversities(universityComboBox);
        yearComboBox.getItems().addAll("1", "2", "3", "4", "5", "6");

        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!signupService.isValidPassword(newVal)) {
                passwordHintLabel.setText("Weak password");
                passwordHintLabel.setStyle("-fx-text-fill: red;");
            } else {
                passwordHintLabel.setText("Strong password");
                passwordHintLabel.setStyle("-fx-text-fill: green;");
            }
        });

        universityComboBox.setOnAction(e -> {
            String selectedUniversity = universityComboBox.getValue();
            if (selectedUniversity != null) {
                universityService.loadFaculties(selectedUniversity, facultyComboBox, majorComboBox);
            }
        });

        facultyComboBox.setOnAction(e -> {
            String selectedFaculty = facultyComboBox.getValue();
            if (selectedFaculty != null) {
                universityService.loadMajors(selectedFaculty, majorComboBox);
            }
        });
        VBox.setMargin(buttonVbox1, new Insets(20, 0, 5, 0));
        VBox.setMargin(buttonVbox2, new Insets(5, 0, 0, 0));
    }

    @FXML
    private void handleSignupClick() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText().trim().toLowerCase();
        String password = passwordField.getText();
        String university = universityComboBox.getValue();
        String faculty = facultyComboBox.getValue();
        String major = majorComboBox.getValue();
        String year = yearComboBox.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() ||
                university == null || faculty == null || major == null || year == null || selectedPdfFile == null) {
            showAlert(MISSING_INFO, FILL_ALL_FIELDS);
            return;
        }

        if (!signupService.isValidStudentEmail(email)) {
            showAlert(INVALID_EMAIL, EMAIL_REQUIREMENTS);
            return;
        }
        if (!signupService.emailContainsNameAndSurname(email, firstName, lastName)) {
            showAlert(INVALID_EMAIL, "Email must contain your first and last name.");
            return;
        }

        if (!signupService.isValidPassword(password)) {
            showAlert(INVALID_PASSWORD, PASSWORD_REQUIREMENTS);
            return;
        }

        String filePath = null;
        if (selectedPdfFile != null) {
            try {
                filePath = signupService.saveStudentDocument(selectedPdfFile);
            } catch (IOException e) {
                showAlert(FILE_UPLOAD_ERROR, FILE_SAVE_FAIL);
                return;
            }
        }

        try {
            signupService.signupStudent(password, firstName, lastName, email,
                    Integer.parseInt(year), university, faculty, major, filePath);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle(SIGNUP_SUCCESS);
            successAlert.setContentText(SIGNUP_SUCCESS_MESSAGE);
            successAlert.show();

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> {
                successAlert.close();
                SceneManager.getInstance().loadScene(SceneLocator.LOGIN_PAGE);
            });
            delay.play();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Signup failed: " + e.getMessage());
        }
    }

    @FXML
    private void handleSignupCancel() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        passwordField.clear();
        universityComboBox.setValue(null);
        facultyComboBox.setValue(null);
        majorComboBox.setValue(null);
        yearComboBox.setValue(null);
    }

    @FXML
    private void handleBackClick() {
        SceneManager.getInstance().loadScene(SceneLocator.LOGIN_PAGE);
    }

    @FXML
    private void handleChooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(SELECT_PDF);
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedPdfFile = file;
            lblSelectedFile.setText(file.getName());
        } else {
            lblSelectedFile.setText(NO_FILE_SELECTED);
        }
    }
}