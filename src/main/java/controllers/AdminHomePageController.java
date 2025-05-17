package controllers;

import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import utils.SceneLocator;

import java.io.IOException;
import java.util.ResourceBundle;

public class AdminHomePageController {

    @FXML
    private BorderPane mainLayout;

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
    private void handleManageUsers(){
        loadCenterContent(SceneLocator.MANAGE_USERS_PAGE);
    }

    @FXML
    private void handleManageStudents(){
        loadCenterContent(SceneLocator.LIST_STUDENTS_PAGE);
    }

    @FXML
    private void handleManageScholarships(){
        loadCenterContent(SceneLocator.MANAGE_SCHOLARSHIPS_PAGE);
    }

    @FXML
    private void handleViewApplications(){
        loadCenterContent(SceneLocator.MANAGE_APPLICATIONS_PAGE);
    }

    @FXML
    private void handleFeedback(){
        loadCenterContent(SceneLocator.MANAGE_ADMIN_FEEDBACK_PAGE);
    }

    @FXML
    private void handleNotification(){
        loadCenterContent(SceneLocator.MANAGE_ADMIN_NOTIFICATION_PAGE);
    }

    @FXML
    private void handleLogout(){
        SceneManager.getInstance().loadScene(SceneLocator.LOGIN_PAGE);
    }

    @FXML
    private void handleFAQ() {
        loadCenterContent(SceneLocator.MANAGE_FAQ_PAGE);
    }

    @FXML
    private void handleNews(){loadCenterContent(SceneLocator.NEWS_ADMIN);}

    @FXML
    private void handleAddUniversities(){loadCenterContent(SceneLocator.MANAGE_UNIVERSITIES);}

}

