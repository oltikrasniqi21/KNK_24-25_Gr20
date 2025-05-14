package controllers;

import Repository.NotificationRepository;
import Services.SceneManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import models.Notification;
import utils.SceneLocator;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StudentNotificationController {

    @FXML
    private VBox notificationPane;

    @FXML
    private TableView<Notification> notificationTable;

    @FXML
    private TableColumn<Notification, String> messageColumn;

    @FXML
    private TableColumn<Notification, String> dateColumn;

    private final NotificationRepository notificationRepository = new NotificationRepository();

    @FXML
    public void initialize() {
        // Hide the pane and make it unmanaged (so it doesn't take space)
        notificationPane.setVisible(false);
        notificationPane.setManaged(false);

        // Set up columns
        messageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMessage()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        dateColumn.setCellValueFactory(cellData -> {
            Timestamp timestamp = cellData.getValue().getCreated_at();
            String formatted = timestamp.toLocalDateTime().format(formatter);
            return new SimpleStringProperty(formatted);
        });
    }

    @FXML
    private void onViewNotificationsClicked() {
        notificationPane.setVisible(true);
        notificationPane.setManaged(true);
        loadNotifications();
    }

    private void loadNotifications() {
        List<Notification> notificationList = notificationRepository.getNotificationsForStudents();

        ObservableList<Notification> observableList = FXCollections.observableArrayList(notificationList);
        notificationTable.setItems(observableList);
    }

    @FXML
    private void handleLogout(){
        SceneManager.getInstance().loadScene(SceneLocator.LOGIN_PAGE);
    }

    @FXML
    private void handleFaqStd() {
        SceneManager.getInstance().loadScene(SceneLocator.MANAGE_FAQ_STUDENT_PAGE);
    }


}
