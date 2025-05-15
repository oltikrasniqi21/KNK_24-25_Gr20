package controllers;

import Repository.NotificationRepository;
import Services.SceneManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import models.Notification;
import utils.SceneLocator;

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

    private final NotificationRepository notificationRepository = new NotificationRepository();
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


    @FXML
    private void onViewNotificationsClicked() {
        notificationsVisible = !notificationsVisible;
        notificationPane.setVisible(notificationsVisible);
        notificationPane.setManaged(notificationsVisible);

        if (notificationsVisible) {
            loadNotifications();
            btnViewNotifications.setText(bundle.getString("hideNotificationsBtn"));
        } else {
            btnViewNotifications.setText(bundle.getString("viewNotificationsBtn"));
        }
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
