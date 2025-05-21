package controllers;

import Services.FeedbackService;
import Services.SceneManager;
import UpdateDTO.UpdateFeedbackDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Feedback;
import utils.SceneLocator;

import java.sql.Timestamp;
import java.util.List;

public class FeedbackController {

    @FXML private TableView<Feedback> feedbackTable;
    @FXML private TableColumn<Feedback, Integer> studentIdCol;
    @FXML private TableColumn<Feedback, String> messageCol;
    @FXML private TableColumn<Feedback, Timestamp> timeStampCol;
    @FXML private TableColumn<Feedback, String> responseCol;

    @FXML private TextArea responseTextArea;
    @FXML private Label statusLabel;

    private final FeedbackService feedbackService = new FeedbackService();
    private final ObservableList<Feedback> feedbackList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up columns
        studentIdCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getUser_id()));
        messageCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMessage()));
        timeStampCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getSubmitted_at()));
        responseCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getResponse()));

        loadFeedback();
    }

    private void loadFeedback() {
        List<Feedback> feedbacks = feedbackService.getAllFeedback();
        feedbackList.setAll(feedbacks);
        feedbackTable.setItems(feedbackList);
    }

    @FXML
    private void handleSubmitResponse() {
        Feedback selected = feedbackTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            statusLabel.setText("No feedback selected.");
            return;
        }

        String responseText = responseTextArea.getText();
        if (responseText.isEmpty()) {
            statusLabel.setText("Response cannot be empty.");
            return;
        }

        UpdateFeedbackDTO updateDTO = new UpdateFeedbackDTO(selected.getFeedback_id(), responseText);
        Feedback updated = feedbackService.submitResponse(updateDTO);

        if (updated != null) {
            statusLabel.setText("Response submitted.");
            loadFeedback();
            responseTextArea.clear();
        } else {
            statusLabel.setText("Failed to update response.");
        }
    }


    @FXML
    private void handleBack(){
        SceneManager.getInstance().loadScene(SceneLocator.ADMIN_HOME_PAGE);
    }
}
