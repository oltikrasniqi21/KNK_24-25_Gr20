package controllers;

import CreateDTO.CreateApplicationDto;
import Exceptions.EmptyFieldException;
import Exceptions.InvalidFieldException;
import Services.ApplicationService;
import Services.CurrentUser;
import Services.LocaleAlertMessages;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import models.Scholarships;
import utils.AlertMessages;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

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

        if (transcriptFile == null){
            throw new EmptyFieldException("Transcript");
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
