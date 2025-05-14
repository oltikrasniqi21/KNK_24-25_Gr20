package controllers;

import Database.DBCustomConnector;
import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import utils.SceneLocator;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StudentFaqController implements Initializable {

    @FXML
    private Label labelFaq;

    @FXML
    private Button btnBack;

    @FXML
    private Accordion faqAccordion;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFAQs();
    }

    public void loadFAQs() {
        String query = "SELECT question, answer FROM faq";

        try (Connection conn = DBCustomConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String question = rs.getString("question");
                String answer = rs.getString("answer");

                Label answerLabel = new Label(answer);
                answerLabel.setWrapText(true); // Allows multiline answers

                TitledPane pane = new TitledPane(question, answerLabel);
                faqAccordion.getPanes().add(pane);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleBackStd() {
        SceneManager.getInstance().loadScene(SceneLocator.STUDENT_HOME_PAGE);
    }
}
