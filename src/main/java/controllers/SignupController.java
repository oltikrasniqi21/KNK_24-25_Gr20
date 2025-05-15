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
import javafx.stage.FileChooser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
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

        // DATABASE INSERTION
        try (Connection conn = Database.DBCustomConnector.getConnection()) {
            conn.setAutoCommit(false);

            String insertUserSQL = "INSERT INTO users (password, first_name, last_name, email, role,status) VALUES (?, ?, ?, ?, ?,?) RETURNING user_id";
            try (PreparedStatement userStmt = conn.prepareStatement(insertUserSQL)) {
                userStmt.setString(1, password);
                userStmt.setString(2, name); // Placeholder, use separate fields if needed
                userStmt.setString(3, surname);
                userStmt.setString(4, email);
                userStmt.setString(5, "student");
                userStmt.setString(6, "pending");



                ResultSet rs = userStmt.executeQuery();
                if (rs.next()) {
                    int userId = rs.getInt("user_id");

                    String insertStudentSQL = "INSERT INTO students (student_id, gpa, year_of_study, university, faculty, major, courses_left, priority, proof_document) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement studentStmt = conn.prepareStatement(insertStudentSQL)) {
                        studentStmt.setInt(1, userId);
                        studentStmt.setDouble(2, 0.0); // Replace with real input if you collect GPA
                        studentStmt.setInt(3, Integer.parseInt(year));
                        studentStmt.setString(4, university);
                        studentStmt.setString(5, faculty);
                        studentStmt.setString(6, major);
                        studentStmt.setInt(7, 3); // Replace with real input if needed
                        studentStmt.setString(8, priority);
                        studentStmt.setString(9, pdfPath);

                        studentStmt.executeUpdate();
                    }
                }

                conn.commit();
                showInfo("Success", "Student registered successfully!");
            } catch (SQLException e) {
                conn.rollback();
                showAlert("Database Error", "Transaction failed: " + e.getMessage());
            }
        } catch (SQLException e) {
            showAlert("Connection Error", "Database connection failed: " + e.getMessage());
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
