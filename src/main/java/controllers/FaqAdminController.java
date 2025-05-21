package controllers;

import Services.FaqService;
import Services.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Faq;
import utils.SceneLocator;

public class FaqAdminController {
    @FXML private TableView<Faq> faqTable;
    @FXML private TableColumn<Faq, String> questionCol;
    @FXML private TableColumn<Faq, String> answerCol;
    @FXML private TextField txtQuestion;
    @FXML private TextArea txtAnswer;

    private final FaqService faqService = new FaqService();
    private ObservableList<Faq> faqList;

    @FXML
    public void initialize() {
        questionCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getQuestion()));
        answerCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getAnswer()));

        faqTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtQuestion.setText(newVal.getQuestion());
                txtAnswer.setText(newVal.getAnswer());
            }
        });

        loadFAQs();
    }

    private void loadFAQs() {
        faqList = FXCollections.observableArrayList(faqService.getAllFaqs());
        faqTable.setItems(faqList);
    }

    @FXML
    private void handleAdd() {
        Faq created = faqService.createFaq(txtQuestion.getText(), txtAnswer.getText());
        if (created != null) {
            loadFAQs();
            clearFields();
        }
    }

    @FXML
    private void handleUpdate() {
        Faq selected = faqTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Faq updated = faqService.updateFaq(selected.getFaq_id(), txtQuestion.getText(), txtAnswer.getText());
            if (updated != null) {
                loadFAQs();
                clearFields();
            }
        }
    }

    @FXML
    private void handleDelete() {
        Faq selected = faqTable.getSelectionModel().getSelectedItem();
        if (selected != null && faqService.deleteFaq(selected.getFaq_id())) {
            loadFAQs();
            clearFields();
        }
    }

    private void clearFields() {
        txtQuestion.clear();
        txtAnswer.clear();
    }

    @FXML
    private void handleBack(){
        SceneManager.getInstance().loadScene(SceneLocator.ADMIN_HOME_PAGE);
    }
}
