package controllers;

import CreateDTO.CreateFeedbackDTO;
import Repository.FeedbackRepository;
import Services.CurrentUser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import models.Feedback;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.ResourceBundle;

public class StudentFeedbackController {

    @FXML
    private TextArea feedbackTextArea;

    @FXML
    private Label statusLabel;

    @FXML
    private TextArea responseTextArea;

    private final FeedbackRepository feedbackRepo = new FeedbackRepository();

    @FXML
    private ResourceBundle resources;

    @FXML
    private void handleSubmitFeedback() {
        String message = feedbackTextArea.getText().trim();
        if (message.isEmpty()) {
            statusLabel.setText(resources.getString("emptyFeedbackWarning")); // optional message
            return;
        }

        // Assume user_id is obtained from session or context
        Integer userId = CurrentUser.getUserId();
        if (userId == null) {
            statusLabel.setText("User not recognized. Please log in.");
            return;
        }
        Timestamp now = Timestamp.from(Instant.now());

        CreateFeedbackDTO dto = new CreateFeedbackDTO(userId, message, now, null);
        Feedback feedback = feedbackRepo.create(dto);

        if (feedback != null) {
            statusLabel.setText(resources.getString("feedbackSuccess"));
            feedbackTextArea.clear();
            loadLatestFeedbackResponse();

        } else {
            statusLabel.setText(resources.getString("feedbackFail"));
        }
    }

    @FXML
    public void initialize() {
        loadLatestFeedbackResponse();
    }


    private void loadLatestFeedbackResponse() {
        Integer userId = CurrentUser.getUserId();
        if (userId == null) {
            responseTextArea.setText("User not recognized.");
            return;
        }

        List<Feedback> allFeedback = feedbackRepo.findAll();
        for (Feedback fb : allFeedback) {
            if (fb.getUser_id() == userId) {
                String response = fb.getResponse();
                if (response != null && !response.isEmpty()) {
                    responseTextArea.setText(response); // or responseLabel.setText(response);
                } else {
                    responseTextArea.setText("No answer yet..."); // or responseLabel.setText("No answer yet...");
                }
                return;
            }
        }

        // No feedback found for this user
        responseTextArea.setText("No feedback found."); // optional
    }
}
