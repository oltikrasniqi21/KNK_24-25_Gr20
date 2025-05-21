package com.example.knk_2425_gr20;

import Services.SceneManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class KNK2025_GR20 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SceneManager sceneManager = SceneManager.getInstance();
        stage.setTitle("Scholarship Application 1.0");
        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Could not load icon: " + e.getMessage());
        }
        stage.setScene(sceneManager.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}