package controllers;

import CreateDTO.CreateScholarshipDTO;
import Exceptions.EmptyFieldException;
import Exceptions.InvalidFieldException;
import Repository.ScholarshipsRepository;
import Services.SceneManager;
import Services.ScholarshipService;
import UpdateDTO.UpdateScholarshipDTO;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Scholarships;
import utils.AlertMessages;
import utils.SceneLocator;

import java.time.LocalDate;

public class EditScholarshipController {

    private final ScholarshipService scholarshipService;
    private final Scholarships passedScholarship = ManageScholarshipsController.passedSelectedScholarship;

    @FXML private TextField nameField;
    @FXML private TextField providerField;
    @FXML private TextField amountField;
    @FXML private DatePicker deadlineField;
    @FXML private TextField gpaField;
    @FXML private TextField yearField;
    @FXML private TextField majorField;

    public EditScholarshipController() {
        this.scholarshipService = new ScholarshipService();
    }

    @FXML
    private void handleBackClick(){
        SceneManager.getInstance().loadScene(SceneLocator.MANAGE_SCHOLARSHIPS_PAGE);
    }

    public void initialize(){
        if(passedScholarship != null){
            nameField.setText(passedScholarship.getScholarship_name());
            nameField.setEditable(false);
            providerField.setText(passedScholarship.getProvider());
            providerField.setEditable(false);
            majorField.setText(passedScholarship.getRequred_major());
            amountField.setText(Integer.toString(passedScholarship.getAmount()));
            yearField.setText(Integer.toString(passedScholarship.getRequired_year()));
            gpaField.setText(Double.toString(passedScholarship.getRequired_gpa()));
            deadlineField.setValue(passedScholarship.getDeadline_date());
        }
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

    private void checkInvalidTypeFields(){
        if(!majorField.getText().matches("[a-zA-Z\\s]+")){
            throw new InvalidFieldException(AlertMessages.MAJOR);
        }

        if(!amountField.getText().matches("^\\d{2,4}$")){
            throw new InvalidFieldException(AlertMessages.AMOUNT);
        }

        if(!yearField.getText().matches("^\\d{1}$")){
            throw new InvalidFieldException(AlertMessages.YEAR);
        }

        if(!gpaField.getText().matches("^[6,7,8,9,10]{1}\\.[0-9]{1,2}$")){
            throw new InvalidFieldException(AlertMessages.GPA);
        }
    };

    @FXML private void handleSaveClick(){
        try{
            checkEmptyFields();
            checkInvalidTypeFields();

            String major = majorField.getText().toLowerCase();
            int amount = Integer.parseInt(amountField.getText());
            int year = Integer.parseInt(yearField.getText());
            double gpa = Double.parseDouble(gpaField.getText());
            LocalDate deadline = deadlineField.getValue();

            UpdateScholarshipDTO scholarshipDTO = new UpdateScholarshipDTO(passedScholarship.getScholarship_id(), amount,deadline,gpa,year,major);
            scholarshipService.update(scholarshipDTO);
            clearFields();
            System.out.println("SaveEdit Working...");
        }catch (EmptyFieldException e) {
            e.getMessage();
        }catch (InvalidFieldException e){
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
