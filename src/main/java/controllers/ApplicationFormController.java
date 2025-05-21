package controllers;

import CreateDTO.CreateApplicationDto;
import Services.ApplicationService;
import Services.CurrentUser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import models.Scholarships;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class ApplicationFormController {

    @FXML private ComboBox<Scholarships> scholarshipComboBox;
    @FXML private TextField gpaField;
    @FXML private Label selectedFileLabel;

    private File transcriptFile;
    private final ApplicationService applicationService;


    public ApplicationFormController(){
        applicationService = new ApplicationService();
    }
    @FXML
    public void initialize(){
        loadScholarships();
    }

    private void loadScholarships(){
        List<Scholarships> scholarships = applicationService.getAllAvailableScholarship();
        scholarshipComboBox.getItems().addAll(scholarships);
        if (!scholarshipComboBox.getItems().isEmpty()){
            scholarshipComboBox.setValue(scholarshipComboBox.getItems().get(0));
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
        try {
            if (!valideInput()) return;

            CreateApplicationDto applicationDto = buildApplicationDto();

            String error = applicationService.submitApplication(applicationDto);
            if (error != null){
                showAlert("Gabim", error);
                return;
            }

            showAlert("Sukses", "Aplikimi u krye me sukses!");
            resetForm();
        }catch (Exception e){
            e.printStackTrace();
            showAlert("Gabim", "Ndodhi nje gabim i papritur.");
        }
    }

    private boolean valideInput(){
        if (scholarshipComboBox.getItems() == null){
            showAlert("Gabim", "Ju lutem zgjedheni nje burse");
            return false;
        }

        if (gpaField.getText().isEmpty()){
            showAlert("Gabim", "Ju lutem shkruani GPA-n tuaj");
            return false;
        }

        try{
            double gpa = Double.parseDouble(gpaField.getText());
            if (gpa <= 6 || gpa >= 10){
                showAlert("Gabim", "GPA duhet te jete midis 6 dhe 10");
                return false;
            }
        }catch (NumberFormatException e){
            showAlert("Gabim", "GPA duhet te jete nje numer valid!");
            return false;
        }

        if (transcriptFile == null){
            showAlert("Gabim", "Ju lutem ngarkoni transkripten tuaj");
            return false;
        }
        return true;
    }

    private CreateApplicationDto buildApplicationDto() {
        return new CreateApplicationDto(
                0,
                CurrentUser.getUserId(),
                scholarshipComboBox.getValue().getScholarship_id(),
                LocalDate.now(),
                transcriptFile.getAbsolutePath(),
                Double.parseDouble(gpaField.getText())
        );
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
