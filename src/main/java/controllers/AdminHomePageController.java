package controllers;

import Services.SceneManager;
import javafx.fxml.FXML;
import utils.SceneLocator;

public class AdminHomePageController {

    @FXML
    private void handleManageUsers(){
        SceneManager.getInstance().loadScene(SceneLocator.MANAGE_USERS_PAGE);
    }

    @FXML
    private void handleManageStudents(){
        SceneManager.getInstance().loadScene(SceneLocator.MANAGE_STUDENTS_PAGE);
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
    private void handleNotification(){
        SceneManager.getInstance().loadScene(SceneLocator.MANAGE_ADMIN_NOTIFICATION_PAGE);
    }

    @FXML
    private void handleLogout(){
        SceneManager.getInstance().loadScene(SceneLocator.LOGIN_PAGE);
    }

    @FXML
    private void handleFAQ() {
        SceneManager.getInstance().loadScene(SceneLocator.MANAGE_FAQ_PAGE);
    }


}
