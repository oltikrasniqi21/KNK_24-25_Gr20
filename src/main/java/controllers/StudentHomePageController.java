package controllers;


import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import utils.SceneLocator;

import java.io.IOException;
import java.util.ResourceBundle;

public class StudentHomePageController {

    @FXML
    private BorderPane mainLayout; // Reference to the main BorderPane (from FXML)

    // Method to load Notifications into the center
    @FXML
    public void loadNotifications() {
         //duhet me kriju ni view tjt
        loadCenterContent(SceneLocator.MANAGE_FAQ_STUDENT_PAGE); // FXML for the Notification view
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

}
