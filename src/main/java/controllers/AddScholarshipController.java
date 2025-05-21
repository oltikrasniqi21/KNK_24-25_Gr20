package controllers;

import CreateDTO.CreateScholarshipDTO;
import Exceptions.EmptyFieldException;
import Exceptions.InvalidFieldException;
import Repository.FacultiesRepository;
import Repository.ScholarshipsRepository;
import Repository.UsersRepository;
import Services.CurrentUser;
import Services.SceneManager;
import Services.ScholarshipService;
import Services.UniversityService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Faculties;
import models.Students;
import models.Users;
import utils.AlertMessages;
import utils.SceneLocator;

import java.time.LocalDate;
import java.util.ArrayList;


public class AddScholarshipController {
    @FXML private TextField nameField;
    @FXML private TextField providerField;
    @FXML private TextField amountField;
    @FXML private DatePicker deadlineField;
    @FXML private TextField gpaField;
    @FXML private ComboBox<String> yearComboBox;
    @FXML private ListView<Faculties> facultiesListView;

    private final ScholarshipService scholarshipService;
    private final FacultiesRepository facultiesRepository;

    public AddScholarshipController(){
        this.scholarshipService = new ScholarshipService();
        this.facultiesRepository  =new FacultiesRepository();
    }

    @FXML private void handleBackClick(){
        SceneManager.getInstance().loadScene(SceneLocator.MANAGE_SCHOLARSHIPS_PAGE);
    }

    @FXML public void initialize(){
        yearComboBox.getItems().addAll("1", "2", "3", "4", "5", "6");

        facultiesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<Faculties> faculties = FXCollections.observableArrayList(facultiesRepository.getAll());
        facultiesListView.setItems(faculties);

        facultiesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Selected: " + newVal);
        });
    }

    private void checkEmptyFields(){
        if (nameField.getText().isEmpty() ||
                providerField.getText().isEmpty() ||
                facultiesListView.getSelectionModel() == null ||
                amountField.getText().isEmpty() ||
                yearComboBox.getValue()==null ||
                gpaField.getText().isEmpty() ||
                deadlineField.getValue() == null) {
            throw new EmptyFieldException();
        }else{
            System.out.println("validation passed....");
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
                checkEmptyFields(); //can throw EmptyFieldExcpetion
                checkInvalidTypeFields(); //can throw InvalidFieldExcpetion

                String name = nameField.getText().toLowerCase();
                String provider = providerField.getText().toLowerCase();
                int amount = Integer.parseInt(amountField.getText());
                int year = Integer.parseInt(yearComboBox.getValue());
                double gpa = Double.parseDouble(gpaField.getText());
                LocalDate deadline = deadlineField.getValue();

                //Kthimi i selected faculties ne arraylist me string te emrave te fakulteteve
                ObservableList<Faculties> selectedItems = facultiesListView.getSelectionModel().getSelectedItems();
                StringBuilder facultiesString = new StringBuilder();

                for(Faculties x: selectedItems){
                    facultiesString.append(x.getName()+" - ");
                    System.out.println("added to StringBuilder");
                }
                System.out.println("FINAL: "+facultiesString);

                CreateScholarshipDTO scholarshipDTO = new CreateScholarshipDTO(name, provider, amount, deadline, gpa, year, facultiesString.toString());
                scholarshipService.create(scholarshipDTO);
                clearFields();
                System.out.println("SaveClick Working...");
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
        amountField.clear();
        facultiesListView.getSelectionModel().clearSelection();
        yearComboBox.getSelectionModel().clearSelection();
        yearComboBox.setValue(null);
        gpaField.clear();
        deadlineField.setValue(null);
    }

}
