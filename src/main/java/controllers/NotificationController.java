package controllers;

import CreateDTO.CreateNotificationDTO;
import Repository.NotificationRepository;
import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Notification;
import utils.SceneLocator;

import java.sql.Timestamp;

public class NotificationController {

    @FXML
    private TextField titleFieldNotification;
    @FXML
    private TextArea messageAreaNotification;
    @FXML
    private Button sendBtnNotification;

    private final NotificationRepository notificationRepository = new NotificationRepository();

    @FXML
    public void handleSendNotification() {
        String title = titleFieldNotification.getText();
        String message = messageAreaNotification.getText();

        if (message == null || message.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Message is required.");
            return;
        }

        CreateNotificationDTO dto = new CreateNotificationDTO();
        dto.setStudent_id(null); // Broadcast message
        dto.setMessage(title + ": " + message); // Combine title and message
        dto.setCreated_at(new Timestamp(System.currentTimeMillis()));
        dto.setRead_status(false);
        dto.setIs_broadcast(true);

        Notification created = notificationRepository.create(dto);

        if (created != null) {
            showAlert(Alert.AlertType.INFORMATION, "Notification sent successfully!");
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to send notification.");
        }
    }

    private void showAlert(Alert.AlertType type, String content) {
        Alert alert = new Alert(type);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        titleFieldNotification.clear();
        messageAreaNotification.clear();
    }

    @FXML
    private void handleBack(){
        SceneManager.getInstance().loadScene(SceneLocator.ADMIN_HOME_PAGE);
    }
}
