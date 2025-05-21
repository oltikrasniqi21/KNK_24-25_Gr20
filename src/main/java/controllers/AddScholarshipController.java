//package controllers;
//
//import CreateDTO.CreateScholarshipDTO;
//import Exceptions.EmptyFieldException;
//import Exceptions.InvalidFieldException;
//import Repository.FacultiesRepository;
//import Repository.ScholarshipsRepository;
//import Repository.UsersRepository;
//import Services.CurrentUser;
//import Services.SceneManager;
//import Services.ScholarshipService;
//import Services.UniversityService;
//import javafx.fxml.FXML;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.DatePicker;
//import javafx.scene.control.TextField;
//import models.Students;
//import models.Users;
//import utils.AlertMessages;
//import utils.SceneLocator;
//
//import java.time.LocalDate;
//
//
//public class AddScholarshipController {
//    @FXML private TextField nameField;
//    @FXML private TextField providerField;
//    @FXML private TextField amountField;
//    @FXML private DatePicker deadlineField;
//    @FXML private TextField gpaField;
//    @FXML private ComboBox<String> yearComboBox;
//    @FXML private ComboBox<String> facultyComboBox;
//
//    private final ScholarshipService scholarshipService;
//    private final UniversityService universityService;
//    private final UsersRepository usersRepository;
//    private final ScholarshipsRepository scholarshipsRepository = new ScholarshipsRepository();
//
//    public AddScholarshipController(){
//        this.universityService = new UniversityService();
//        this.scholarshipService = new ScholarshipService();
//        this.usersRepository  =new UsersRepository();
//    }
//
//    @FXML private void handleBackClick(){
//        SceneManager.getInstance().loadScene(SceneLocator.MANAGE_SCHOLARSHIPS_PAGE);
//    }
//
//    @FXML public void initialize(){
//        yearComboBox.getItems().addAll("1", "2", "3", "4", "5", "6");
//
//
//    }
//
//    private void checkEmptyFields(){
//        if (nameField.getText().isEmpty() ||
//                providerField.getText().isEmpty() ||
//                majorField.getText().isEmpty() ||
//                amountField.getText().isEmpty() ||
//                yearField.getText().isEmpty() ||
//                gpaField.getText().isEmpty() ||
//                deadlineField.getValue() == null) {
//            throw new EmptyFieldException();
//        }
//    };
//
//    private void checkInvalidTypeFields(){
//        if(!majorField.getText().matches("[a-zA-Z\\s]+")){
//            throw new InvalidFieldException(AlertMessages.MAJOR);
//        }
//
//        if(!amountField.getText().matches("^\\d{2,4}$")){
//            throw new InvalidFieldException(AlertMessages.AMOUNT);
//        }
//
//        if(!yearField.getText().matches("^\\d{1}$")){
//            throw new InvalidFieldException(AlertMessages.YEAR);
//        }
//
//        if(!gpaField.getText().matches("^[6,7,8,9,10]{1}\\.[0-9]{1,2}$")){
//            throw new InvalidFieldException(AlertMessages.GPA);
//        }
//    };
//
//    @FXML private void handleSaveClick(){
//            try{
//                checkEmptyFields(); //can throw EmptyFieldExcpetion
//                checkInvalidTypeFields(); //can throw InvalidFieldExcpetion
//
//                String name = nameField.getText().toLowerCase();
//                String provider = providerField.getText().toLowerCase();
//                String major = majorField.getText().toLowerCase();
//                int amount = Integer.parseInt(amountField.getText());
//                int year = Integer.parseInt(yearField.getText());
//                double gpa = Double.parseDouble(gpaField.getText());
//                LocalDate deadline = deadlineField.getValue();
//
//                CreateScholarshipDTO scholarshipDTO = new CreateScholarshipDTO(name, provider, amount, deadline, gpa, year, major);
//                scholarshipService.create(scholarshipDTO);
//                clearFields();
//                System.out.println("SaveClick Working...");
//            }catch (EmptyFieldException e) {
//                e.getMessage();
//            }catch (InvalidFieldException e){
//                e.getMessage();
//            }
//    }
//
//    @FXML private void handleClearClick(){
//        clearFields();
//    }
//
//    private void clearFields(){
//        nameField.clear();
//        providerField.clear();
//        majorField.clear();
//        amountField.clear();
//        yearField.clear();
//        gpaField.clear();
//        deadlineField.setValue(null);
//    }
//
//    private void loadFaculties(){
//        Users currentUser = usersRepository.getById(CurrentUser.getUserId());
//        Students student = usersRepository.gget
//    }
//}
