package controllers;

import Database.DBCustomConnector;
import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.SceneLocator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class CreateUsersController {

    @FXML
    private TextField txtEmri;

    @FXML
    private TextField txtMbiemri;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField pwdPassword;

    public CreateUsersController() {}



        @FXML
        private void handleShtoUserClick() {
            String emri = txtEmri.getText();
            String mbiemri = txtMbiemri.getText();
            String email = txtEmail.getText();
            String password = pwdPassword.getText();

            String emailRegex = "^[^@\\s]+@admin\\.uni\\-[a-z]{2,3}\\.edu$";
            Pattern pattern = Pattern.compile(emailRegex);

            // Input validation
            if (emri.isEmpty() || mbiemri.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Please fill all fields.");
                return;
            }

            if (!pattern.matcher(email).matches()) {
                showAlert("Invalid Email", "Please enter a valid admin email (e.g. user@admin.uni-pr.com).");
                return;
            }

            try (Connection conn = DBCustomConnector.getConnection()) {
                // Check for existing email
                if (emailExists(conn, email)) {
                    showAlert("Duplicate Email", "This email is already registered.");
                    return;
                }

                // Generate salt and hash password
                String salt = utils.PasswordUtils.getSalt();
                String hashedPassword = utils.PasswordUtils.hashPassword(password, salt);
                String passwordToStore = salt + "$" + hashedPassword;

                // Insert admin user with hashed password
                String query = "INSERT INTO users (password, first_name, last_name, email, role, status) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement statement = conn.prepareStatement(query)) {
                    statement.setString(1, passwordToStore);
                    statement.setString(2, emri);
                    statement.setString(3, mbiemri);
                    statement.setString(4, email);
                    statement.setString(5, "admin");
                    statement.setNull(6, java.sql.Types.VARCHAR); // or setString(6, "pending");

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        showAlert("Success", "Admin user added successfully.");
                        clearFields();
                    } else {
                        showAlert("Error", "Failed to add user.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Database Error", "Failed to process user creation: " + e.getMessage());
            }
        }

    private boolean emailExists(Connection conn, String email) throws SQLException {
            String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            return false;
        }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        txtEmri.clear();
        txtMbiemri.clear();
        txtEmail.clear();
        pwdPassword.clear();
    }

    @FXML
    private void handleBackClick(){
        SceneManager.getInstance().loadScene(SceneLocator.MANAGE_USERS_PAGE);
    }


}
