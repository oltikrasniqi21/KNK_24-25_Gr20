package controllers;

import Services.LanguageManager;
import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.stage.FileChooser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignupController implements Initializable {


    private SceneManager sceneManager;
    private final LanguageManager languageManager = LanguageManager.getInstance();

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label lblSelectedFile;
    @FXML
    private TextField NameField;
    @FXML
    private TextField SurnameField;

    @FXML
    private ComboBox<String> universityComboBox;
    @FXML
    private ComboBox<String> facultyComboBox;
    @FXML
    private ComboBox<String> majorComboBox;
    @FXML
    private ComboBox<String> yearComboBox;
    @FXML
    private ComboBox<String> semesterComboBox;
    @FXML
    private ComboBox<String> priorityComboBox;


    private File selectedPdfFile;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        universityComboBox.getItems().addAll("University of Prishtina", "UBT", "AAB", "RIT Kosovo");
        facultyComboBox.getItems().addAll("Engineering", "Medicine", "Law", "Economics", "Arts");
        majorComboBox.getItems().addAll("Computer Science", "Business", "Civil Engineering", "Law", "Medicine");
        yearComboBox.getItems().addAll("1", "2", "3", "4");
        semesterComboBox.getItems().addAll("Spring", "Fall");
        priorityComboBox.getItems().addAll("veteran", "disabled", "none");
        priorityComboBox.setValue("none");
    }

    @FXML
    private void handleSignupClick() throws IOException {
        String name = NameField.getText();
        String surname = SurnameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String university = universityComboBox.getValue();
        String faculty = facultyComboBox.getValue();
        String major = majorComboBox.getValue();
        String year = yearComboBox.getValue();
        String semester = semesterComboBox.getValue();
        String priority = priorityComboBox.getValue();
        if ("none".equalsIgnoreCase(priority)) {
            priority = null;
        }

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty() || university == null || faculty == null ||
                major == null || year == null || semester == null) {
            showAlert("Missing Information", "Please fill out all fields.");
            return;
        }

        if (!isValidStudentEmail(email)) {
            showAlert("Invalid Email", "Email must be a valid university student address (e.g. user@student.uni-pr.edu)");
            return;
        }

        String pdfPath = null;
        if (selectedPdfFile != null) {
            File uploadDir = new File("uploads");
            if (!uploadDir.exists() && !uploadDir.mkdirs()) {
                showAlert("Directory Error", "Failed to create upload directory.");
                return;
            }

            File dest = new File(uploadDir, selectedPdfFile.getName());
            Files.copy(selectedPdfFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            pdfPath = dest.getAbsolutePath();
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

    @FXML
    private void handleSignupCancel() {
        emailField.clear();
        passwordField.clear();
        universityComboBox.setValue(null);
        facultyComboBox.setValue(null);
        majorComboBox.setValue(null);
        yearComboBox.setValue(null);
        semesterComboBox.setValue(null);
        priorityComboBox.setValue(null);
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


    @FXML
    private void handleENLanguageClick() throws Exception {
        LanguageManager.getInstance().setLocale(Locale.ENGLISH);
        SceneManager.reload();
    }

    @FXML
    private void handleSQLanguageClick() throws Exception {
        LanguageManager.getInstance().setLocale(new Locale("sq"));
        SceneManager.reload();
    }

}