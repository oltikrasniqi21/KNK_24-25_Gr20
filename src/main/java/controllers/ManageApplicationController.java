package controllers;

import CreateDTO.CreateNotificationDTO;
import Repository.ApplicationsRepository;
import Repository.NotificationRepository;
import Repository.ScholarshipsRepository;
import Repository.UsersRepository;
import Services.SceneManager;
import UpdateDTO.UpdateApplicationsDTO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import models.Applications;
import models.ApplicationsDetails;
import utils.SceneLocator;

import java.awt.*;
import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ManageApplicationController {
    @FXML
    private TableView<Applications> applicationsTable;
    @FXML
    private TableColumn<Applications, Integer> applicationIdColumn;
    @FXML
    private TableColumn<Applications, String> studentNameColumn;
    @FXML
    private TableColumn<Applications, String> scholarshipNameColumn;
    @FXML
    private TableColumn<Applications, String> applicationDateColumn;
    @FXML
    private TableColumn<Applications, String> gpaColumn;
    @FXML
    private TableColumn<Applications, String> statusColumn;

    @FXML private TextField studentField;
    @FXML private TextField emailField;
    @FXML private TextField gpaField;
    @FXML private TextField priorityField;
    @FXML private TextField scholarshipField;
    @FXML private TextField requiredGpaField;
    @FXML private TextField requiredYearField;
    @FXML private TextField deadlineField;
    @FXML private TextField statusField;
    @FXML private TextField currentYearField;
    @FXML private TextField applicationDateField;

    private ApplicationsRepository applicationsRepository;
    private UsersRepository usersRepository;
    private ScholarshipsRepository scholarshipsRepository;

    public ManageApplicationController(){
        applicationsRepository = new ApplicationsRepository();
        usersRepository = new UsersRepository();
        scholarshipsRepository = new ScholarshipsRepository();
    }

    public void initialize(){
        applicationIdColumn.setCellValueFactory(cellData ->{
            Applications applications = cellData.getValue();
            return new SimpleIntegerProperty(applications.getApplicationId()).asObject();
        });

        studentNameColumn.setCellValueFactory(cellData ->{
            Applications applications = cellData.getValue();
            return new SimpleStringProperty(getStudentNameById(applications.getStudentId()));
        });

        scholarshipNameColumn.setCellValueFactory(cellData ->{
            Applications applications = cellData.getValue();
            return new SimpleStringProperty(getScholarshipNameById(applications.getScholarshipId()));
        });

        applicationDateColumn.setCellValueFactory(cellData ->{
            Applications applications = cellData.getValue();
            return new SimpleStringProperty(applications.getApplicationDate().toString());
        });

        gpaColumn.setCellValueFactory(cellData ->{
            Applications applications = cellData.getValue();
            return new SimpleStringProperty(String.valueOf(applications.getGpa()));
        });

        statusColumn.setCellValueFactory(cellData ->{
            Applications applications = cellData.getValue();
            return new SimpleStringProperty(applications.getStatus());
        });

        applicationsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Applications selectedApplication = applicationsTable.getSelectionModel().getSelectedItem();
                if (selectedApplication != null) {
                    displayApplicationDetails(selectedApplication);
                }
            }
        });

        loadApplications();
    }

    private void loadApplications(){
        try{
            List<Applications> applications = applicationsRepository.getAll();
            applicationsTable.getItems().setAll(applications);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleStatusUpdate(String status){
        Applications selectedApplication = applicationsTable.getSelectionModel().getSelectedItem();
        if(selectedApplication != null){
            int appId = selectedApplication.getApplicationId();
            UpdateApplicationsDTO updateDTO = new UpdateApplicationsDTO(appId, status);
            applicationsRepository.update(updateDTO);

            // Send notification based on status
            if ("Approved".equalsIgnoreCase(status)) {
                sendNotificationToStudent(selectedApplication.getStudentId(), selectedApplication.getScholarshipId(), "approved");
            } else if ("Rejected".equalsIgnoreCase(status)) {
                sendNotificationToStudent(selectedApplication.getStudentId(), selectedApplication.getScholarshipId(), "rejected");
            }

            loadApplications();
        }
    }


    private void sendNotificationToStudent(int studentId, int scholarshipId, String status) {
        NotificationRepository notificationRepo = new NotificationRepository();

        String scholarshipName = getScholarshipNameById(scholarshipId);
        String message;

        if ("approved".equalsIgnoreCase(status)) {
            message = "Your application for the '" + scholarshipName + "' scholarship has been approved.";
        } else {
            message = "Your application for the '" + scholarshipName + "' scholarship has been rejected.";
        }

        CreateNotificationDTO notificationDTO = new CreateNotificationDTO(
                studentId,
                message,
                new Timestamp(System.currentTimeMillis()),
                false, // not seen yet
                false  // not broadcast
        );

        notificationRepo.create(notificationDTO);
    }


    private String getStudentNameById(int studentId){
        return usersRepository.getStudentNameById(studentId);
    }

    private String getScholarshipNameById(int scholarshipId){
        return scholarshipsRepository.getScholarshipName(scholarshipId);
    }

    private void displayApplicationDetails(Applications selectedApplication) {
        ApplicationsDetails details = applicationsRepository.getApplicationDetailsById(selectedApplication.getApplicationId());
        if (details != null ){
            studentField.setText(details.studentName);
            emailField.setText(details.email);
            gpaField.setText(String.valueOf(details.gpa));
            priorityField.setText(details.priority);
            scholarshipField.setText(details.scholarshipName);
            requiredGpaField.setText(String.valueOf(details.requiredGpa));
            requiredYearField.setText(String.valueOf(details.requiredYear));
            deadlineField.setText(details.deadline.toString());
            statusField.setText(details.status);
            currentYearField.setText(String.valueOf(details.currentYear));
            applicationDateField.setText(details.applicationDate.toString());
        }
    }

    @FXML
    private void handleViewTranscript(){
        Applications selectedApplication = applicationsTable.getSelectionModel().getSelectedItem();
        if (selectedApplication != null){
            ApplicationsDetails details = applicationsRepository.getApplicationDetailsById(selectedApplication.getApplicationId());
            if (details != null && details.transcriptPath != null && !details.transcriptPath.isEmpty()){
                File file = new File(details.transcriptPath);
                if (file.exists()){
                    try{
                        Desktop.getDesktop().open(file);
                    } catch(Exception e){
                        e.printStackTrace();
                        new Alert(Alert.AlertType.ERROR, "Gabim gjate hapjes se transkriptes.").showAndWait();
                    }
                }else {
                    new Alert(Alert.AlertType.ERROR, "Transkripta nuk u gjet ne path-in e dhene.").showAndWait();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Nuk ka transkripte te lidhur me kete aplikim.").showAndWait();
            }
        }
    }

    @FXML
    private void handleApprove(){
        handleStatusUpdate("Approved");
    }

    @FXML
    private void handleReject(){
        handleStatusUpdate("Rejected");
    }

    @FXML
    private void handleClose(){
        SceneManager.getInstance().loadScene(SceneLocator.ADMIN_HOME_PAGE);
    }
}
