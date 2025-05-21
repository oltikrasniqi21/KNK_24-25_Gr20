package controllers;

import Services.ScholarshipTagsService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.News;
import Services.StudentNewsService;

import java.io.IOException;
import java.util.List;

public class StudentNewsController {

    @FXML
    private GridPane newsGrid;

    private final ScholarshipTagsService scholarshipTagsService = new ScholarshipTagsService();
    private final StudentNewsService newsService = new StudentNewsService();



    @FXML
    public void initialize() {
        loadNews();
    }

    private void loadNews() {
        List<News> newsList = newsService.getAllNews();

        newsGrid.getChildren().clear();


        int column = 0;
        int row = 0;
        int maxColumns = 3;

        for (News news : newsList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/news_card.fxml"));
                VBox card = loader.load();

                NewsCardController controller = loader.getController();

                String tagName = scholarshipTagsService.getTagNameById(news.getScholarshipTagId());

                controller.setData(
                        news.getTitle(),
                        news.getSummary(),
                        tagName,
                        news.getImagePath()
                );

                newsGrid.add(card, column, row);

                column++;
                if (column == maxColumns) {
                    column = 0;
                    row++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
