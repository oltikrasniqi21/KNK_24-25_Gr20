package test;

import Services.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import utils.SceneLocator;

public class SceneManagerTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SceneManager sceneManager = SceneManager.getInstance();
        stage.setScene(sceneManager.getScene());
        stage.show();
    }




}
