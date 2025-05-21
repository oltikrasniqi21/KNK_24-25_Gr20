package controllers;

import Services.NotificationService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import models.Notification;

import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class StudentNotificationController implements Initializable {

    @FXML
    private VBox notificationPane;

    @FXML
    private TableView<Notification> notificationTable;

    @FXML
    private TableColumn<Notification, String> messageColumn;

    @FXML
    private TableColumn<Notification, String> dateColumn;

    @FXML
    private javafx.scene.control.Button btnViewNotifications;

    private boolean notificationsVisible = false;

    private ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources; // Capture the resource bundle

        notificationPane.setVisible(false);
        notificationPane.setManaged(false);

        messageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMessage()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        dateColumn.setCellValueFactory(cellData -> {
            Timestamp timestamp = cellData.getValue().getCreated_at();
            String formatted = timestamp.toLocalDateTime().format(formatter);
            return new SimpleStringProperty(formatted);
        });
    }


    private final NotificationService notificationService = new NotificationService();

    //perdoret te homepage controller tash
    @FXML
    public void loadNotifications() {
        List<Notification> notificationList = notificationService.getNotificationsForCurrentStudent();
        ObservableList<Notification> observableList = FXCollections.observableArrayList(notificationList);
        notificationTable.setItems(observableList);
    }




}
