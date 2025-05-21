package com.example.knk_2425_gr20;

import Services.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class KNK2025_GR20 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SceneManager sceneManager = SceneManager.getInstance();
        stage.setScene(sceneManager.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}