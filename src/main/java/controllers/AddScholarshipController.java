package controllers;

import CreateDTO.CreateScholarshipDTO;
import Exceptions.EmptyFieldException;
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

    private void checkEmptyFields(){
        if (nameField.getText().isEmpty() ||
                providerField.getText().isEmpty() ||
                majorField.getText().isEmpty() ||
                amountField.getText().isEmpty() ||
                yearField.getText().isEmpty() ||
                gpaField.getText().isEmpty() ||
                deadlineField.getValue() == null) {
            throw new EmptyFieldException();
        }
    };

    @FXML private void handleSaveClick(){
            try{
                checkEmptyFields();

                String name = nameField.getText().toLowerCase();
                String provider = providerField.getText().toLowerCase();
                String major = majorField.getText().toLowerCase();
                int amount = Integer.parseInt(amountField.getText());
                int year = Integer.parseInt(yearField.getText());
                double gpa = Double.parseDouble(gpaField.getText());
                LocalDate deadline = deadlineField.getValue();

                CreateScholarshipDTO scholarshipDTO = new CreateScholarshipDTO(name, provider, amount, deadline, gpa, year, major);
                scholarshipService.create(scholarshipDTO);
                clearFields();
                System.out.println("SaveClick Working...");
            }catch (EmptyFieldException e) {
                e.getMessage();
            }
    }

    @FXML private void handleClearClick(){
        clearFields();
    }

    private void clearFields(){
        nameField.clear();
        providerField.clear();
        majorField.clear();
        amountField.clear();
        yearField.clear();
        gpaField.clear();
        deadlineField.setValue(null);
    }
}
