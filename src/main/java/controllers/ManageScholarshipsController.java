package controllers;

import Repository.ScholarshipsRepository;
import Services.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.TextField;
import models.Applications;
import models.Scholarships;
import utils.SceneLocator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @FXML private TextField searchStudent;

    private ScholarshipsRepository scholarshipsRepository;

    public ManageScholarshipsController(){
        this.scholarshipsRepository = new ScholarshipsRepository();
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

        scholarshipsProviderColumn.setCellValueFactory(cellData ->{
            Scholarships scholarship = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(scholarship.getRequred_major());
        });
    }

    @FXML private void loadScholarships(){
        try{
            ArrayList<Scholarships> applications = scholarshipsRepository.getAll();
            scholarshipsTable.getItems().setAll(applications);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void handleBackClick(){
        SceneManager.getInstance().loadScene(SceneLocator.ADMIN_HOME_PAGE);
    }

    @FXML private void handleSearchClick(){

    }

    @FXML private void handleAddScholarshipClick(){
        SceneManager.getInstance().loadScene(SceneLocator.ADD_SCHOLARSHIPS_PAGE);
    }
}
