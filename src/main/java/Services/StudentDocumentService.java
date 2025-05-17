package Services;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDocumentService {

    private final Connection connection;

    public StudentDocumentService(Connection connection) {
        this.connection = connection;
    }

    public void openStudentDocument(int studentId) {
        String query = "SELECT document_path FROM students WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String path = rs.getString("document_path");

                if (path == null || path.isBlank()) {
                    showAlert("No document", "This student has not uploaded a document.");
                    return;
                }

                File file = new File(path);
                if (!file.exists()) {
                    showAlert("File not found", "The document path is saved, but the file does not exist.");
                    return;
                }

                if (!Desktop.isDesktopSupported()) {
                    showAlert("Unsupported", "Desktop API is not supported on this system.");
                    return;
                }

                Desktop.getDesktop().open(file);

            } else {
                showAlert("Not found", "No student found with the given ID.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open document: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}