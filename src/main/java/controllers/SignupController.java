package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import Database.DBCustomConnector;
import Services.SignupService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
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

    @FXML private ComboBox<String> universityComboBox;
    @FXML private ComboBox<String> facultyComboBox;
    @FXML private ComboBox<String> majorComboBox;
    @FXML private ComboBox<String> yearComboBox;

    private File selectedPdfFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        universityComboBox.getItems().addAll("University of Prishtina", "UBT", "AAB", "RIT Kosovo");
        facultyComboBox.getItems().addAll("Engineering", "Medicine", "Law", "Economics", "Arts");
        majorComboBox.getItems().addAll("Computer Science", "Business", "Civil Engineering", "Law", "Medicine");
        yearComboBox.getItems().addAll("1", "2", "3", "4");
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
            showInfo("Signup Successful", "Your account has been created.");
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
