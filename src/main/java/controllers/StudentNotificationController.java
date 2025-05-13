package controllers;

import Repository.NotificationRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Notification;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class StudentNotificationController implements Initializable {

    @FXML
    private TableView<Notification> notificationTable;

    @FXML
    private TableColumn<Notification, String> messageColumn;

    @FXML
    private TableColumn<Notification, Timestamp> dateColumn;

    private final NotificationRepository notificationRepo = new NotificationRepository();

    private int studentId = 123; // <-- Replace with logged-in student ID

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("created_at"));
    }


    public void onViewNotificationsClicked(ActionEvent actionEvent) {

    }
}
