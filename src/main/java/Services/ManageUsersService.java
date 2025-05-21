package Services;

import Database.DBCustomConnector;
import Repository.UsersRepository;
import javafx.scene.control.Alert;
import models.Users;

import java.util.List;

public class ManageUsersService {
    private final UsersRepository usersRepository;
    private final StudentDocumentService documentService;

    public ManageUsersService() {
        this.usersRepository = new UsersRepository();
        this.documentService = new StudentDocumentService(DBCustomConnector.getConnection());
    }

    public List<Users> getAllStudentUsers() {
        return usersRepository.getAllStudentUsers();
    }

    public List<Users> searchUsers(String searchTerm) {
        return usersRepository.searchUsers(searchTerm);
    }

    public boolean updateUserStatus(int userId, String newStatus) {
        String query = "UPDATE users SET status = ? WHERE id = ?";
        try {
            int rowsAffected = usersRepository.updateUserStatus(userId, newStatus);
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void openStudentDocument(int userId) {
        new Thread(() -> documentService.openStudentDocument(userId)).start();
    }

    public void showAlert(String title, String message) {
        javafx.application.Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}