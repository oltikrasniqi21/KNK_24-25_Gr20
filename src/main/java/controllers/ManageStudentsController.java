package controllers;

import Services.SceneManager;
import javafx.fxml.FXML;
import utils.SceneLocator;

public class ManageStudentsController {

    @FXML
    private void handleAddStudentClick(){

    }

    @FXML
    private void handleSearchClick(){

    }

    @FXML
    private void handleBackClick(){
        SceneManager.getInstance().loadScene(SceneLocator.ADMIN_HOME_PAGE);
    }
}
