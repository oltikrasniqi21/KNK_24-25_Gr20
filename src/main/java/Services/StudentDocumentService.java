package Services;

import Repository.UsersRepository;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.awt.Desktop;
import java.io.File;

public class StudentDocumentService {
    private final UsersRepository usersRepository;

    public StudentDocumentService() {
        this.usersRepository = new UsersRepository();
    }

    public void openStudentDocument(int studentId) {
        String documentPath = usersRepository.getStudentDocumentPathById(studentId);
        if (documentPath == null || documentPath.isBlank()) {
            showAlert("No document", "This student has not uploaded a document.");
            return;
        }
        File file = new File(documentPath);
        if (!file.exists()) {
            showAlert("File not found", "The document path is saved, but the file does not exist.");
            return;
        }
        if (!Desktop.isDesktopSupported()) {
            showAlert("Unsupported", "Desktop API is not supported on this system.");
            return;
        }
        try {
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            showAlert("Error", "Could not open the document: " + e.getMessage());
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

