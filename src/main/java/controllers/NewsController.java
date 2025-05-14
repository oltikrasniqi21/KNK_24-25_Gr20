package controllers;

import javafx.collections.ObservableList;
import CreateDTO.CreateNewsDTO;
import Repository.NewsRepository;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import models.News;

import java.sql.Date;

public class NewsController {

    @FXML
    private TextArea titleField;
    @FXML
    private TextArea contentField;
    @FXML
    private DatePicker visibilityDatePicker;
    @FXML
    private ChoiceBox<Integer> scholarshipChoiceBox;

    @FXML
    private TableView<News> newsTable;
    @FXML
    private TableColumn<News, String> newsTitleCol;
    @FXML
    private TableColumn<News, String> newsScholarshipCol;
    @FXML
    private TableColumn<News, Date> newsVisibleUntilCol;
    @FXML
    private TableColumn<News, Void> newsActionCol;


    private NewsRepository newsRepository = new NewsRepository();

    @FXML
    private void publishNews() {
        // Get the values from the form fields
        String title = titleField.getText();
        String content = contentField.getText();
        Date visibleUntil = Date.valueOf(visibilityDatePicker.getValue());

        // If the scholarshipChoiceBox is not null, get selected value
        Integer scholarshipId = scholarshipChoiceBox.getValue() != null ? scholarshipChoiceBox.getValue() : null;

        // Assuming the logged-in user ID is available, this could be hardcoded or fetched from the session
        int postedBy = 1; // Replace with actual user ID logic

        // Create the DTO object with the gathered data
        CreateNewsDTO createNewsDTO = new CreateNewsDTO(title, content, scholarshipId, postedBy, visibleUntil);

        // Call the repository to insert the news into the database
        newsRepository.create(createNewsDTO);

        // Optionally, show a confirmation message or reset the form fields
        System.out.println("News published successfully!");

        loadNews();

        // Clear the form fields after publishing
        titleField.clear();
        contentField.clear();
        visibilityDatePicker.setValue(null);
        scholarshipChoiceBox.setValue(null);
    }

    private void addDeleteButtonToTable() {
        Callback<TableColumn<News, Void>, TableCell<News, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<News, Void> call(final TableColumn<News, Void> param) {
                return new TableCell<>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction(event -> {
                            News news = getTableView().getItems().get(getIndex());
                            handleDelete(news);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
            }
        };

        newsActionCol.setCellFactory(cellFactory);
    }

    @FXML
    public void initialize() {
        // Set up columns
        newsTitleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        newsScholarshipCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getScholarshipId())));
        newsVisibleUntilCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getVisibleUntil()));

        // Load news data into table
        loadNews();

        addDeleteButtonToTable();
    }

    private void loadNews() {
        ObservableList<News> newsData = FXCollections.observableArrayList(newsRepository.getAllNews());
        newsTable.setItems(newsData);
    }

    private void handleDelete(News news) {
        // Show confirmation dialog before deletion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete News");
        alert.setHeaderText("Are you sure you want to delete this news item?");
        alert.setContentText("This action cannot be undone.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            // Delete the item from the database
            boolean isDeleted = newsRepository.delete(news.getNewsId());
            if (isDeleted) {
                // Remove from the table as well
                newsTable.getItems().remove(news);
            } else {
                // Show error if deletion failed
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Deletion Failed");
                errorAlert.setHeaderText("Failed to delete the news item.");
                errorAlert.showAndWait();
            }

            loadNews();

        }
    }
}