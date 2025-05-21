package controllers;

import Database.DBCustomConnector;
import Services.FaqService;
import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import models.Faq;
import utils.SceneLocator;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentFaqController implements Initializable {

    @FXML
    private Label labelFaq;

    @FXML
    private Button btnBack;

    @FXML
    private Accordion faqAccordion;
    private final FaqService faqService = new FaqService();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFAQs();
    }

    public void loadFAQs() {
        for (Faq faq : faqService.getAllFaqs()) {
            Label answerLabel = new Label(faq.getAnswer());
            answerLabel.setWrapText(true);
            TitledPane pane = new TitledPane(faq.getQuestion(), answerLabel);
            faqAccordion.getPanes().add(pane);
        }
    }

}
