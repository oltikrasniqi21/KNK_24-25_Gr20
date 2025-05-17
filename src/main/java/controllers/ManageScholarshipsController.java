package controllers;

import Repository.ScholarshipsRepository;
import Services.LocaleAlertMessages;
import Services.SceneManager;
import Services.ScholarshipService;
import UpdateDTO.UpdateScholarshipDTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import models.Applications;
import models.Scholarships;
import utils.AlertMessages;
import utils.SceneLocator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageScholarshipsController {
    @FXML private TableView<Scholarships> scholarshipsTable;
    @FXML private TableColumn<Scholarships, Integer> scholarshipsIdColumn;
    @FXML private TableColumn<Scholarships, String> scholarshipsNameColumn;
    @FXML private TableColumn<Scholarships, String> scholarshipsProviderColumn;
    @FXML private TableColumn<Scholarships, Integer> scholarshipsAmountColumn;
    @FXML private TableColumn<Scholarships, String> scholarshipsDeadlineColumn;
    @FXML private TableColumn<Scholarships, Double> scholarshipsGpaColumn;
    @FXML private TableColumn<Scholarships, Integer> scholarshipsYearColumn;
    @FXML private TableColumn<Scholarships, String> scholarshipsMajorColumn;
    @FXML private TableColumn<Scholarships, String> scholarshipsStatusColumn;

    @FXML private TextField searchStudent;
    @FXML private Button btnEdit;

    private ScholarshipsRepository scholarshipsRepository;
    private ScholarshipService scholarshipService;
    public static Scholarships passedSelectedScholarship;

    public ManageScholarshipsController(){
        this.scholarshipsRepository = new ScholarshipsRepository();
        this.scholarshipService= new ScholarshipService();
    }

    public void initialize(){
        scholarshipsIdColumn.setCellValueFactory(cellData ->{
            Scholarships scholarship = cellData.getValue();
            return new javafx.beans.property.SimpleIntegerProperty(scholarship.getScholarship_id()).asObject();
        });

        scholarshipsNameColumn.setCellValueFactory(cellData ->{
            Scholarships scholarship = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(scholarship.getScholarship_name());
        });

        scholarshipsProviderColumn.setCellValueFactory(cellData ->{
            Scholarships scholarship = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(scholarship.getProvider());
        });

        scholarshipsAmountColumn.setCellValueFactory(cellData ->{
            Scholarships scholarship = cellData.getValue();
            return new javafx.beans.property.SimpleIntegerProperty(scholarship.getAmount()).asObject();
        });

        scholarshipsDeadlineColumn.setCellValueFactory(cellData ->{
            Scholarships scholarship = cellData.getValue();
            LocalDate deadline = scholarship.getDeadline_date();
            String formatted = deadline != null ? deadline.toString() : "N/A";

            return new javafx.beans.property.SimpleStringProperty(formatted);
        });

        scholarshipsGpaColumn.setCellValueFactory(cellData ->{
            Scholarships scholarships = cellData.getValue();
            return new javafx.beans.property.SimpleDoubleProperty(scholarships.getRequired_gpa()).asObject();
        });

        scholarshipsYearColumn.setCellValueFactory(cellData ->{
            Scholarships scholarships = cellData.getValue();
            return new javafx.beans.property.SimpleIntegerProperty(scholarships.getRequired_year()).asObject();
        });

        scholarshipsMajorColumn.setCellValueFactory(cellData ->{
            Scholarships scholarship = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(scholarship.getRequred_major());
        });

        scholarshipsStatusColumn.setCellValueFactory(cellData ->{
            Scholarships scholarship = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(scholarship.getStatus());
        });

        loadScholarships();
    }

    private void loadScholarships(){
        try{
            ArrayList<Scholarships> scholarships = scholarshipsRepository.getAll();
            scholarshipsTable.getItems().setAll(scholarships);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void handleBackClick(){
        SceneManager.getInstance().loadScene(SceneLocator.ADMIN_HOME_PAGE);
    }

    @FXML private void handleSearchClick(){
        String currentSearch = searchStudent.getText().toLowerCase();

        if (currentSearch == ""){
            initialize();
            System.out.println("inicliazimi");
        }else{
            char[] searchChars = currentSearch.toLowerCase().toCharArray();
            ArrayList<Scholarships> scholarships = scholarshipsRepository.getAll();
            ArrayList<Scholarships> matchedScholarships = new ArrayList<>();

            for(Scholarships x : scholarships){
                char[] chars = x.getScholarship_name().toLowerCase().toCharArray();
                boolean match = false;

                for(int i=0; i<searchChars.length;i++){
                    if(chars[i] == searchChars[i]){
                        match=true;
                    }
                }

                if(match == true){
                    matchedScholarships.add(x);
                }
            }
            scholarshipsTable.getItems().setAll(matchedScholarships);
            System.out.println("Load from search");
        }
    }

    @FXML private void handleAddScholarshipClick(){
        SceneManager.getInstance().loadScene(SceneLocator.ADD_SCHOLARSHIPS_PAGE);
    }

    @FXML private void handleEditClick(){
        passedSelectedScholarship = scholarshipsTable.getSelectionModel().getSelectedItem();
        if(passedSelectedScholarship == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, LocaleAlertMessages.getLocalizedMessage(AlertMessages.SELECT_ROW_BUNDLE));
            alert.showAndWait();
        }else{
            SceneManager.getInstance().loadScene(SceneLocator.EDIT_SCHOLARSHIPS_PAGE);
        }

    }

    @FXML private void handleDeleteClick(){
        passedSelectedScholarship = scholarshipsTable.getSelectionModel().getSelectedItem();

        if(passedSelectedScholarship == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, LocaleAlertMessages.getLocalizedMessage(AlertMessages.SELECT_ROW_BUNDLE));
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, LocaleAlertMessages.getLocalizedMessage(AlertMessages.DELETE_CONFIRMATION, AlertMessages.SCHOLARSHIP));
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                UpdateScholarshipDTO updateScholarshipDTO = new UpdateScholarshipDTO(passedSelectedScholarship);
                updateScholarshipDTO.setActiveStatus(false);
                scholarshipService.update(updateScholarshipDTO);
                System.out.println("Status Changed");
            } else {
                System.out.println("User cancelled the action.");
            }
        }
    }
}
