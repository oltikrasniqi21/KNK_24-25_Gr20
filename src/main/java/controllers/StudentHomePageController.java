package controllers;


import Repository.NotificationRepository;
import Services.CurrentUser;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Notification;
import utils.SceneLocator;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import Services.NotificationService;

public class StudentHomePageController implements Initializable {
    @FXML
    private VBox notificationPane;

    @FXML
    private HBox topHbox;

    private List<javafx.scene.control.Button> menuButtons;
    private int currentIndex = 0;

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
    private final NotificationService notificationService = new NotificationService();

    @FXML
    public void loadNotifications() {
        List<Notification> notificationList = notificationService.getNotificationsForCurrentStudent();
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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;

        notificationPane.setVisible(false);
        notificationPane.setManaged(false);

        messageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMessage()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        dateColumn.setCellValueFactory(cellData -> {
            Timestamp timestamp = cellData.getValue().getCreated_at();
            String formatted = timestamp.toLocalDateTime().format(formatter);
            return new SimpleStringProperty(formatted);
        });

        // ===== Navigimi me tastierë për VBox me butona =====
        menuButtons = topHbox.getChildren().stream()
                .filter(node -> node instanceof javafx.scene.control.Button)
                .map(node -> (javafx.scene.control.Button) node)
                .toList();

        if (!menuButtons.isEmpty()) {
            menuButtons.get(currentIndex).requestFocus();
        }
        topHbox.setOnKeyPressed(event -> handleKeyPress(event));

        javafx.application.Platform.runLater(() -> topHbox.requestFocus());
    }

    private void handleKeyPress(javafx.scene.input.KeyEvent event) {
        switch (event.getCode()) {
            case UP -> {
                if (currentIndex > 0) {
                    currentIndex--;
                    menuButtons.get(currentIndex).requestFocus();
                }
                event.consume();
            }
            case DOWN -> {
                if (currentIndex < menuButtons.size() - 1) {
                    currentIndex++;
                    menuButtons.get(currentIndex).requestFocus();
                }
                event.consume();
            }
            case ENTER -> {
                menuButtons.get(currentIndex).fire(); // e aktivizon butonin aktual
                event.consume();
            }
        }
    }



    @FXML
    private void loadFeedbackForm() {
        loadCenterContent(SceneLocator.STUDENT_FEEDBACK_PAGE);
    }

    @FXML
    private void onViewApplicationClicked(){
        loadCenterContent(SceneLocator.APPLICATION_FORM);
    }

    @FXML
    private void loadNewsStudent() {
        loadCenterContent(SceneLocator.NEWS_STUDENT);
    }

    @FXML
    private void handleViewProfileClick(){loadCenterContent(SceneLocator.MY_PROFILE_PAGE);}

}
