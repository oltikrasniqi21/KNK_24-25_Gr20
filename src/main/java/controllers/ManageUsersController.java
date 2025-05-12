package controllers;

import Services.SceneManager;
import javafx.fxml.FXML;
import utils.SceneLocator;

public class ManageUsersController {

    @FXML
    private void handleBackClick(){
        SceneManager.getInstance().loadScene(SceneLocator.ADMIN_HOME_PAGE);
    }

    @FXML
    private void handleAddUserClick(){
        SceneManager.getInstance().loadScene(SceneLocator.CREATE_USERS_PAGE);
    }

    @FXML
    private void handleSearchClick(){

    }
}
