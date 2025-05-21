package controllers;

import Services.SceneManager;
import javafx.collections.ObservableList;
import CreateDTO.CreateNewsDTO;
import Repository.NewsRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import models.News;
import utils.SceneLocator;

import java.io.File;


public class NewsController {

    @FXML
    private TextField titleField;
    @FXML
    private TextArea contentField;

    @FXML
    private ChoiceBox<Integer> scholarshipTagChoiceBox;

    @FXML
    private TableView<News> newsTable;
    @FXML
    private TableColumn<News, String> newsTitleCol;
    @FXML
    private TableColumn<News, String> newsScholarshipCol;
    @FXML
    private TableColumn<News, Void> newsActionCol;
    @FXML
    private TableColumn<News, String> newsSummaryCol;
    @FXML
    private TextArea summaryField;
    @FXML
    private VBox LVbox;
    @FXML
    private VBox RVbox;
    @FXML
    private Label lblSelectedImage;
    @FXML
    private File selectedImageFile;


    private final NewsRepository newsRepository = new NewsRepository();

    @FXML
    private void publishNews() {
        // Get the values from the form fields
        String title = titleField.getText();
        String content = contentField.getText();
        String summary = summaryField.getText();


        if (selectedImageFile == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Image Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an image before publishing.");
            alert.showAndWait();
            return; // stop publishing if no image
        }

        String imagePath = selectedImageFile.getPath();

        // If the scholarshipChoiceBox is not null, get selected value
        Integer scholarshipTagId = scholarshipTagChoiceBox.getValue() != null ? scholarshipTagChoiceBox.getValue() : null;

        int postedBy = 1; // Replace with actual user ID logic

        CreateNewsDTO createNewsDTO = new CreateNewsDTO(title, content, scholarshipTagId, postedBy, summary, imagePath);

        newsRepository.create(createNewsDTO);

        System.out.println("News published successfully!");

        loadNews();

        // Clear the form fields after publishing
        titleField.clear();
        contentField.clear();
        summaryField.clear();
        scholarshipTagChoiceBox.setValue(null);
        selectedImageFile = null;
        lblSelectedImage.setText("No file selected");
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
        newsSummaryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSummary()));
        newsScholarshipCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getScholarshipTagId())));

        // Load news data into table
        loadNews();
        addDeleteButtonToTable();
        newsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HBox.setMargin(LVbox, new Insets(30, 30, 30, 70));
        HBox.setMargin(RVbox, new Insets(30, 30, 30, 70));
        HBox.setMargin(titleField, new Insets(60, 0, 70, 0));
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

    @FXML
    private void backToAdmin(javafx.event.ActionEvent event){
        SceneManager.getInstance().loadScene(SceneLocator.ADMIN_HOME_PAGE);
    }


    @FXML
    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedImageFile = file;
            lblSelectedImage.setText(file.getName());
        } else {
            lblSelectedImage.setText("No file selected");
        }
    }
}