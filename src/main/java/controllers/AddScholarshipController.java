package controllers;

import CreateDTO.CreateScholarshipDTO;
import Repository.ScholarshipsRepository;
import Services.SceneManager;
import Services.ScholarshipService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import utils.SceneLocator;

import java.time.LocalDate;


public class AddScholarshipController {
    @FXML private TextField nameField;
    @FXML private TextField providerField;
    @FXML private TextField amountField;
    @FXML private DatePicker deadlineField;
    @FXML private TextField gpaField;
    @FXML private TextField yearField;
    @FXML private TextField majorField;

    private ScholarshipsRepository scholarshipsRepository;
    private ScholarshipService scholarshipService;

    public AddScholarshipController(){
        this.scholarshipService = new ScholarshipService();
        this.scholarshipsRepository = new ScholarshipsRepository();
    }

    @FXML private void handleBackClick(){
        SceneManager.getInstance().loadScene(SceneLocator.MANAGE_SCHOLARSHIPS_PAGE);
    }

    @FXML private void handleSaveClick(){
        String name = nameField.getText();
        String provider = providerField.getText();
        String major = majorField.getText();
        int amount = Integer.parseInt(amountField.getText());
        int year = Integer.parseInt(yearField.getText());
        double gpa = Double.parseDouble(gpaField.getText());
        LocalDate deadline = deadlineField.getValue();

        CreateScholarshipDTO scholarshipDTO = new CreateScholarshipDTO(name,provider,amount,deadline,gpa,year,major);
        scholarshipService.create(scholarshipDTO);
        System.out.println("SaveClick Working...");

    }

    @FXML private void handleClearClick(){

    }
}
