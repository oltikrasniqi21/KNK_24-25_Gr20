package controllers;

import Repository.ScholarshipsRepository;
import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import utils.SceneLocator;


public class AddScholarshipController {
    @FXML private TextField nameField;
    @FXML private TextField providerField;
    @FXML private TextField amountField;
    @FXML private TextField deadlineField;
    @FXML private TextField gpaField;
    @FXML private TextField yearField;
    @FXML private TextField majorField;

    private ScholarshipsRepository scholarshipsRepository;

    public AddScholarshipController(){
        this.scholarshipsRepository = new ScholarshipsRepository();
    }

    @FXML private void handleBackClick(){
        SceneManager.getInstance().loadScene(SceneLocator.MANAGE_SCHOLARSHIPS_PAGE);
    }

    @FXML private void handleSaveClick(){

    }

    @FXML private void handleClearClick(){

    }
}
