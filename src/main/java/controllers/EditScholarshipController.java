package controllers;

import Exceptions.EmptyFieldException;
import Exceptions.InvalidFieldException;
import Repository.FacultiesRepository;
import Repository.UniversitiesRepository;
import Services.SceneManager;
import Services.ScholarshipService;
import Services.UniversityService;
import UpdateDTO.UpdateScholarshipDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Faculties;
import models.Scholarships;
import utils.AlertMessages;
import utils.SceneLocator;

import java.time.LocalDate;
import java.util.ArrayList;

public class EditScholarshipController {

    private final ScholarshipService scholarshipService;
    private final FacultiesRepository facultiesRepository;
    private final Scholarships passedScholarship = ManageScholarshipsController.passedSelectedScholarship;

    @FXML private TextField amountField;
    @FXML private DatePicker deadlineField;
    @FXML private TextField gpaField;
    @FXML private ComboBox<String> yearComboBox;
    @FXML private ListView<Faculties> facultiesListView;
    @FXML private Label prevSelected;

    public EditScholarshipController() {
        this.facultiesRepository = new FacultiesRepository();
        this.scholarshipService = new ScholarshipService();
    }

    @FXML
    private void handleBackClick(){
        SceneManager.loadCenterContent(SceneLocator.MANAGE_SCHOLARSHIPS_PAGE);
    }


    public void initialize(){
        if(passedScholarship != null){
            amountField.setText(Integer.toString(passedScholarship.getAmount()));
            yearComboBox.setValue(Integer.toString(passedScholarship.getRequired_year()));
            gpaField.setText(Double.toString(passedScholarship.getRequired_gpa()));
            deadlineField.setValue(passedScholarship.getDeadline_date());
            prevSelected.setText("Prev.: "+ passedScholarship.getRequired_faculties());

            facultiesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            ObservableList<Faculties> faculties = FXCollections.observableArrayList(facultiesRepository.getAll());
            facultiesListView.setItems(faculties);

        }
    }

    private void checkEmptyFields(){
        if (facultiesListView.getSelectionModel() == null||
                amountField.getText().isEmpty() ||
                yearComboBox.getValue() ==null ||
                gpaField.getText().isEmpty() ||
                deadlineField.getValue() == null) {
            throw new EmptyFieldException();
        }
    };

    private void checkInvalidTypeFields(){
        if(!amountField.getText().matches("^\\d{2,4}$")){
            throw new InvalidFieldException(AlertMessages.AMOUNT);
        }

        if(!gpaField.getText().matches("^[6,7,8,9,10]{1}\\.[0-9]{1,2}$")){
            throw new InvalidFieldException(AlertMessages.GPA);
        }
    };

    @FXML private void handleSaveClick(){
        try{
            checkEmptyFields();
            checkInvalidTypeFields();

            int amount = Integer.parseInt(amountField.getText());
            int year = Integer.parseInt(yearComboBox.getValue());
            double gpa = Double.parseDouble(gpaField.getText());
            LocalDate deadline = deadlineField.getValue();

            ObservableList<Faculties> selectedItems = facultiesListView.getSelectionModel().getSelectedItems();
            StringBuilder facultiesString = new StringBuilder();

            for(Faculties x: selectedItems){
                facultiesString.append(x.getName()+" - ");
                System.out.println("added to StringBuilder");
            }
            System.out.println("FINAL: "+facultiesString);

            UpdateScholarshipDTO scholarshipDTO = new UpdateScholarshipDTO(passedScholarship.getScholarship_id(), amount,deadline,gpa,year,facultiesString.toString());
            scholarshipService.update(scholarshipDTO);
            clearFields();
            System.out.println("SaveEdit Working...");
        }catch (EmptyFieldException e) {
            e.getMessage();
        }catch (InvalidFieldException e){
            e.getMessage();
        }
    }

    @FXML private void handleResetClick(){
        clearFields();
    }

    private void clearFields(){
        facultiesListView.getSelectionModel().clearSelection();
        amountField.setText(Integer.toString(passedScholarship.getAmount()));
        yearComboBox.setValue(Integer.toString(passedScholarship.getRequired_year()));
        gpaField.setText(Double.toString(passedScholarship.getRequired_gpa()));
        deadlineField.setValue(passedScholarship.getDeadline_date());
    }
}
