package controllers;

import CreateDTO.CreateApplicationDto;
import Repository.ApplicationsRepository;
import Repository.ScholarshipsRepository;
import Services.CurrentUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import models.Applications;
import models.Scholarships;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ApplicationFormController {

    @FXML private ComboBox<Scholarships> scholarshipComboBox;
    @FXML private TextField gpaField;
    @FXML private Label selectedFileLabel;

    private File transcriptFile;
    private final ScholarshipsRepository scholarshipsRepository;
    private final ApplicationsRepository applicationsRepository;


    public ApplicationFormController(){
        scholarshipsRepository = new ScholarshipsRepository();
        applicationsRepository = new ApplicationsRepository();
    }
    @FXML
    public void initialize(){
        loadScholarships();
    }

    private void loadScholarships(){
        List<Scholarships> scholarships = scholarshipsRepository.getAll();
        scholarshipComboBox.getItems().addAll(scholarships);

        if (!scholarships.isEmpty()){
            scholarshipComboBox.setValue(scholarships.get(0));
        }
    }

    @FXML
    private void handleUploadTranscript(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zgjidh Transkripten");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        transcriptFile = fileChooser.showOpenDialog(selectedFileLabel.getScene().getWindow());
        if (transcriptFile != null){
            selectedFileLabel.setText(transcriptFile.getName());
        }else {
            selectedFileLabel.setText("Asnje skedare i zgjedhur");
        }
    }

    @FXML
    private void handleSubmitApplication(){
        Scholarships selectedScholarship = scholarshipComboBox.getValue();
        String gpaText = gpaField.getText();

        if (selectedScholarship == null){
            showAlert("Gabim", "Ju lutem zgjedheni nje burse");
            return;
        }

        if (gpaText.isEmpty()){
            showAlert("Gabim", "Ju lutem shkruani GPA-n tuaj");
            return;
        }

        if (transcriptFile == null){
            showAlert("Gabim", "Ju lutem ngarkoni transkripten tuaj");
            return;
        }

        try {
            double gpa = Double.parseDouble(gpaText);
            if (gpa <= 6 || gpa >= 10){
                showAlert("Gabim", "GPA duhet te jete midis 6 dhe 10");
                return;
            }

            CreateApplicationDto applicationDto = new CreateApplicationDto(
                    0,
                    CurrentUser.getUserId(),
                    selectedScholarship.getScholarship_id(),
                    LocalDate.now(),
                    transcriptFile.getAbsolutePath(),
                    gpa
            );

            applicationsRepository.create(applicationDto);

            showAlert("Sukses", "Ju aplikuat me sukses!");
            resetForm();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void resetForm(){
        scholarshipComboBox.setValue(null);
        gpaField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
