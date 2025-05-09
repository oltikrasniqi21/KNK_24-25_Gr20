package controllers;

import Services.SceneManager;
import javafx.fxml.FXML;
import utils.SceneLocator;

public class AdminHomePageController {

    @FXML
    private void handleManageUsers(){
        SceneManager.getInstance().loadScene(SceneLocator.ADMIN_HOME_PAGE);
    }

    @FXML
    private void handleManageScholarships(){
        SceneManager.getInstance().loadScene(SceneLocator.MANAGE_SCHOLARSHIPS_PAGE);
    }

    @FXML
    private void handleViewApplications(){
        SceneManager.getInstance().loadScene(SceneLocator.MANAGE_APPLICATIONS_PAGE);
    }

    @FXML
    private void handleFeedback(){
        SceneManager.getInstance().loadScene(SceneLocator.MANAGE_FEEDBACK_PAGE);
    }

    @FXML
    private void handleNotifications(){
        SceneManager.getInstance().loadScene(SceneLocator.MANAGE_NOTIFICATIONS_PAGE);
    }

    @FXML
    private void handleLogout(){
        SceneManager.getInstance().loadScene(SceneLocator.LOGIN_PAGE);
    }
}
