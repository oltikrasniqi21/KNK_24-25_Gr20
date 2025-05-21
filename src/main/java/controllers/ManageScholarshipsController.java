package controllers;

import Repository.ScholarshipsRepository;
import Services.LocaleAlertMessages;
import Services.SceneManager;
import Services.ScholarshipService;
import UpdateDTO.UpdateScholarshipDTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import models.Scholarships;
import utils.AlertMessages;
import utils.SceneLocator;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @FXML private TableColumn<Scholarships, String> scholarshipsFacultiesColumn;
    @FXML private TableColumn<Scholarships, String> scholarshipsStatusColumn;

    @FXML private TextField searchStudent;
    @FXML private Button btnEdit;

    private final ScholarshipsRepository scholarshipsRepository;
    private final ScholarshipService scholarshipService;
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

        scholarshipsFacultiesColumn.setCellValueFactory(cellData ->{
            Scholarships scholarship = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(scholarship.getRequired_faculties());
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
        SceneManager.loadCenterContent(SceneLocator.ADD_SCHOLARSHIPS_PAGE);
    }

    @FXML private void handleEditClick(){
        passedSelectedScholarship = scholarshipsTable.getSelectionModel().getSelectedItem();
        if(passedSelectedScholarship == null){
            LocaleAlertMessages.showInformationAlert(AlertMessages.SELECT_ROW_BUNDLE);
        }else{
            SceneManager.loadCenterContent(SceneLocator.EDIT_SCHOLARSHIPS_PAGE);
        }

    }

    private void refreshTable(){
        ArrayList<Scholarships> scholarships = scholarshipsRepository.getAll();
        scholarshipsTable.getItems().setAll(scholarships);
    }

    @FXML private void handleDeleteClick(){
        passedSelectedScholarship = scholarshipsTable.getSelectionModel().getSelectedItem();

        if(passedSelectedScholarship.getStatus().equals("deactive")){
            System.out.println("DEACTIVE...");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, LocaleAlertMessages.getLocalizedMessage(AlertMessages.DELETE_CONFIRMATION, AlertMessages.SCHOLARSHIP));
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                scholarshipsRepository.delete(passedSelectedScholarship.getScholarship_id());
                refreshTable();
                return;
            } else {
                System.out.println("User cancelled the action.");
            }
        }else{
            passedSelectedScholarship.getStatus();
            System.out.println("Bursa nuk eshte deaktive");
        }

        if(passedSelectedScholarship == null){
            LocaleAlertMessages.showErrorAlert(AlertMessages.SELECT_ROW_BUNDLE);
        }else{
            System.out.println("ACTIVE...");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, LocaleAlertMessages.getLocalizedMessage(AlertMessages.DEACTIVE_CONFIRMATION, AlertMessages.SCHOLARSHIP));
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                UpdateScholarshipDTO updateScholarshipDTO = new UpdateScholarshipDTO(passedSelectedScholarship);
                updateScholarshipDTO.setActiveStatus(false);
                scholarshipService.update(updateScholarshipDTO);
                System.out.println("Status Changed");
                refreshTable();
                return;
            } else {
                System.out.println("User cancelled the action.");
            }

            try{
                SceneManager.reload();
            }catch (Exception e){
                e.getMessage();
            }
        }
    }
}
