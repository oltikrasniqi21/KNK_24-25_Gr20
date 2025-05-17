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
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import models.Notification;
import utils.SceneLocator;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class StudentHomePageController implements Initializable {
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


    @FXML
    private BorderPane mainLayout; // Reference to the main BorderPane (from FXML)

    // Method to load Notifications into the center
    @FXML
    public void loadNotifications() {
        List<Notification> notificationList = notificationRepository.getNotificationsForStudents();
        ObservableList<Notification> observableList = FXCollections.observableArrayList(notificationList);
        notificationTable.setItems(observableList);
    }

    // Method to load FAQ into the center
    @FXML
    public void loadFAQ() {
        loadCenterContent(SceneLocator.MANAGE_FAQ_STUDENT_PAGE);
    }

    // Helper method to load any FXML into the center
    private void loadCenterContent(String fxmlFile) {
        try {
            // Load the resource bundle inside the method
            ResourceBundle bundle = ResourceBundle.getBundle("languages.message");

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile), bundle);
            Node content = loader.load();
            mainLayout.setCenter(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleLogout(){
        SceneManager.getInstance().loadScene(SceneLocator.LOGIN_PAGE);
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

    @FXML private void handleViewProfileClick(){
        SceneManager.getInstance().loadScene(SceneLocator.MY_PROFILE_PAGE);
    }

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
    private void loadFeedbackForm() {
        loadCenterContent(SceneLocator.STUDENT_FEEDBACK_PAGE);
    }

    @FXML
    private void onViewApplicationClicked(){
        loadCenterContent(SceneLocator.APPLICATION_FORM);
    }
}
