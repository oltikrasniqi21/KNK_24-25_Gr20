package controllers;

import CreateDTO.CreateNewsDTO;
import Repository.NewsRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;

public class NewsController {

    @FXML private TextArea titleField;
    @FXML private TextArea contentField;
    @FXML private DatePicker visibilityDatePicker;
    @FXML private ChoiceBox<Integer> scholarshipChoiceBox;

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

        // Clear the form fields after publishing
        titleField.clear();
        contentField.clear();
        visibilityDatePicker.setValue(null);
        scholarshipChoiceBox.setValue(null);
    }
}

