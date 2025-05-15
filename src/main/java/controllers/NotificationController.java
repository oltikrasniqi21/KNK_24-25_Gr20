package controllers;

import CreateDTO.CreateNotificationDTO;
import Repository.NotificationRepository;
import Services.SceneManager;
import UpdateDTO.UpdateNotificationDTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import models.Notification;
import utils.SceneLocator;

import java.sql.Timestamp;

public class NotificationController {

    @FXML
    private TextField titleFieldNotification;
    @FXML
    private TextArea messageAreaNotification;
    @FXML
    private TableView<Notification> notificationTable;

    @FXML
    private TableColumn<Notification, String> messageColumn;

    @FXML
    private TableColumn<Notification, Void> editColumn;

    private final NotificationRepository notificationRepository = new NotificationRepository();

    @FXML
    private void initialize() {
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        messageColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        messageColumn.setOnEditCommit(event -> {
            Notification notification = event.getRowValue();
            notification.setMessage(event.getNewValue());

            UpdateNotificationDTO dto = new UpdateNotificationDTO();
            dto.setNotificationId(notification.getNotification_id()); // Ensure Notification model has getId()
            dto.setMessage(notification.getMessage());
            dto.setRead_status(notification.isRead_status());

            Notification success = notificationRepository.update(dto);
            if (success == null) {
                showAlert(Alert.AlertType.ERROR, "Failed to update notification in the database.");
            }

            notificationTable.refresh();
        });

        addEditButtonToTable();

        notificationTable.setEditable(true);
        loadNotifications(); // Load initial data
    }

    private void loadNotifications() {
        notificationTable.getItems().clear();
        notificationTable.getItems().addAll(notificationRepository.getAll());
    }

    private void addEditButtonToTable() {
        editColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(e -> {
                    Notification n = getTableView().getItems().get(getIndex());
                    showEditDialog(n); // Optional dialog method
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });
    }


    private void showEditDialog(Notification notification) {
        TextInputDialog dialog = new TextInputDialog(notification.getMessage());
        dialog.setTitle("Edit Notification");
        dialog.setHeaderText("Edit Message:");
        dialog.setContentText("Message:");

        dialog.showAndWait().ifPresent(newMessage -> {
            notification.setMessage(newMessage);

            UpdateNotificationDTO dto = new UpdateNotificationDTO();
            dto.setNotificationId(notification.getNotification_id());
            dto.setMessage(newMessage);
            dto.setRead_status(notification.isRead_status());

            notificationRepository.update(dto);
            notificationTable.refresh();
        });
    }



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
