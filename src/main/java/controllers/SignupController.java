package controllers;

import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import Database.DBCustomConnector;
import Services.SignupService;
import utils.SceneLocator;
import javafx.animation.PauseTransition;
import javafx.util.Duration;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SignupController implements Initializable {

    private final SignupService signupService;

    public SignupController() {
        this.signupService = new SignupService(DBCustomConnector.getConnection());
    }

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label lblSelectedFile;
    @FXML private Label passwordHintLabel;

    @FXML private ComboBox<String> universityComboBox;
    @FXML private ComboBox<String> facultyComboBox;
    @FXML private ComboBox<String> majorComboBox;
    @FXML private ComboBox<String> yearComboBox;

    private File selectedPdfFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        universityComboBox.getItems().addAll("University of Prishtina", "University of Prizren", "University of Gjilan", "University of Gjakova", "University of Mitrovica");
        facultyComboBox.getItems().addAll("FIEK", "Medicine", "Law", "Economics", "Arts","FIM","FIN","Architecture","Education");
        majorComboBox.getItems().addAll("Software Engineer", "Business", "Civil Engineering", "Law", "Dentistry","Robotics Engineering","Data Science","Cyber Security");
        yearComboBox.getItems().addAll("1", "2", "3", "4", "5", "6");

        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!isValidPassword(newVal)) {
                passwordHintLabel.setText("Weak password");
                passwordHintLabel.setStyle("-fx-text-fill: red;");
            } else {
                passwordHintLabel.setText("Strong password");
                passwordHintLabel.setStyle("-fx-text-fill: green;");
            }
        });

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
                university == null || faculty == null || major == null || year == null){
            showAlert("Missing Information", "Please fill out all fields.");
            return;
        }

        if (!isValidStudentEmail(email)) {
            showAlert("Invalid Email", "Email must be a valid university student address (e.g. user@student.uni-pr.edu)");
            return;
        }

        if (!isValidPassword(password)) {
            showAlert(
                    "Invalid Password",
                    "Password must contain:\n" +
                            "• At least 8 characters\n" +
                            "• At least one uppercase letter\n" +
                            "• At least one lowercase letter\n" +
                            "• At least one digit\n" +
                            "• At least one special character (e.g. !@#$%^&*)"
            );
            return;
        }

        if (selectedPdfFile != null) {
            try {
                File uploadDir = new File("uploads");
                if (!uploadDir.exists() && !uploadDir.mkdirs()) {
                    showAlert("Directory Error", "Failed to create upload directory.");
                    return;
                }

                File dest = new File(uploadDir, selectedPdfFile.getName());
                Files.copy(selectedPdfFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                showAlert("File Upload Error", "Could not save your document.");
                return;
            }
        }

        try {
            signupService.signupStudent(password, firstName, lastName, email,
                    Integer.parseInt(year), university, faculty, major);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Signup Successful");
            successAlert.setContentText("Your account has been created.");
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
    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
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
        fileChooser.setTitle("Select PDF Document");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedPdfFile = file;
            lblSelectedFile.setText(file.getName());
        } else {
            lblSelectedFile.setText("No file selected");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    private boolean isValidStudentEmail(String email) {
        String regex = "^[\\w.-]+@student\\.uni-[a-z]{2,10}\\.edu$";
        return Pattern.matches(regex, email);
    }
}
