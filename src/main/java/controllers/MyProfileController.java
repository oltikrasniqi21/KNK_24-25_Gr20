package controllers;

import Database.DBCustomConnector;
import Exceptions.EmptyFieldException;
import Exceptions.InvalidFieldException;
import Repository.UsersRepository;
import Services.CurrentUser;
import Services.LocaleAlertMessages;
import Services.SceneManager;
import Services.SignupService;
import UpdateDTO.UpdateUserDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Users;
import utils.AlertMessages;
import utils.PasswordUtils;
import utils.SceneLocator;
import javafx.scene.layout.*;

import java.sql.Connection;

import static utils.AlertMessages.*;


public class MyProfileController {
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private PasswordField oldPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private VBox passwordPane;
    @FXML private Label passMatchLabel;
    @FXML private Label passwordHintLabel;

    private final UsersRepository usersRepository;
    private final Integer currentUserId;
    private final SignupService signupService;
    private Connection connection = DBCustomConnector.getConnection();
    private final Users currUser;
    private boolean editable = false;

    public MyProfileController(){
        usersRepository = new UsersRepository();
        currentUserId = CurrentUser.getUserId();
        signupService = new SignupService(this.connection);
        currUser = usersRepository.getById(currentUserId);
    }

    public void initialize(){
        passwordPane.setVisible(false);
        if(currentUserId == null){
            System.out.println("CurrentUser ID is null!");
            return;
        }

        Users user = usersRepository.getById(currentUserId);
        System.out.println("usere email "+user.getEmail());
        String[] nameLast = usersRepository.getStudentNameById(currentUserId).split(" ");
        String name = nameLast[0];
        String lastName = nameLast[1];

        firstNameField.setText(name);
        firstNameField.setEditable(false);
        lastNameField.setText(lastName);
        lastNameField.setEditable(false);
        emailField.setText(user.getEmail());
        emailField.setEditable(false);
    }

    @FXML private void handleBackClick() {
        SceneManager.getInstance().loadScene(SceneLocator.STUDENT_HOME_PAGE);
    }

    @FXML private void handleEditClick(){
            firstNameField.setEditable(true);
            lastNameField.setEditable(true);
            emailField.setEditable(true);
            editable=true;
    }

    @FXML private void handleSaveClick(){
        if(passwordPane.isVisible()==true){
            handlePasswordChange();
            handleInfoChange();
        }else{
            handleInfoChange();
        }
    }

    private void handleInfoChange(){
        if(editable==true){
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();


            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                handleCancelClick();
                throw new EmptyFieldException();
            }

            if(!signupService.isValidStudentEmail(email)){
                handleCancelClick();
                throw new InvalidFieldException(AlertMessages.EMAIL);
            }

            if(!firstName.matches("^[A-Z][a-z]{1,29}$")){
                handleCancelClick();
                throw new InvalidFieldException(AlertMessages.NAME);
            }

            if(!lastName.matches("^[A-Z][a-z]{1,29}$")){
                handleCancelClick();
                throw new InvalidFieldException(AlertMessages.LAST_NAME);
            }


            UpdateUserDTO updateUserDTO = new UpdateUserDTO(currUser);
            updateUserDTO.setEmail(email);
            updateUserDTO.setFirstName(firstName);
            updateUserDTO.setLastName(lastName);

            usersRepository.updateInfo(updateUserDTO);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, LocaleAlertMessages.getLocalizedMessage(AlertMessages.SUCCESSFUL_EDIT_BUNDLE));
            alert.showAndWait();
        }
    }

    @FXML private void handleCancelClick(){
        String[] nameLast = usersRepository.getStudentNameById(currentUserId).split(" ");
        String name = nameLast[0];
        String lastName = nameLast[1];

        firstNameField.setText(name);
        firstNameField.setEditable(false);
        lastNameField.setText(lastName);
        lastNameField.setEditable(false);
        emailField.setText(currUser.getEmail());
        emailField.setEditable(false);
        editable=false;

        oldPasswordField.clear();
        newPasswordField.clear();
    }


    @FXML private void handleChangePasswordClick(){
        if(passwordPane.isVisible()==true){
            passwordPane.setVisible(false);
            oldPasswordField.clear();
            newPasswordField.clear();
        }else{
            passMatchLabel.setText(" ");
            passwordPane.setVisible(true);

            String oldPassword = usersRepository.getHashedPassword(CurrentUser.getUserId());
            //Ndryshimi dinamik i label
            oldPasswordField.textProperty().addListener((obs, oldVal, newVal) -> {
                if (PasswordUtils.checkPasswordMatch(newVal, oldPassword)) {
                    passMatchLabel.setText(LocaleAlertMessages.getLocalizedMessage(AlertMessages.MATCH));
                    passMatchLabel.setStyle("-fx-text-fill: green;");
                } else {
                    passMatchLabel.setText(LocaleAlertMessages.getLocalizedMessage(AlertMessages.NO_MATCH));
                    passMatchLabel.setStyle("-fx-text-fill: red;");
                }
            });

            newPasswordField.textProperty().addListener((obs, oldVal, newVal) -> {
                if (!signupService.isValidPassword(newVal)) {
                    passwordHintLabel.setText(LocaleAlertMessages.getLocalizedMessage(AlertMessages.WEAK_PASSWORD));
                    passwordHintLabel.setStyle("-fx-text-fill: red;");
                } else {
                    passwordHintLabel.setText(LocaleAlertMessages.getLocalizedMessage(AlertMessages.STRONG_PASSWORD));
                    passwordHintLabel.setStyle("-fx-text-fill: green;");
                }
            });
        }
    }

    private void handlePasswordChange(){
        if(passMatchLabel.getText().equals(LocaleAlertMessages.getLocalizedMessage(AlertMessages.MATCH))){
            if(passwordHintLabel.getText() == LocaleAlertMessages.getLocalizedMessage(AlertMessages.STRONG_PASSWORD)){
                String newPasswordInput = newPasswordField.getText();
                String newSalt = PasswordUtils.getSalt();
                String newHashedPassword = PasswordUtils.hashPassword(newPasswordInput, newSalt);
                String finalNewPassword = newSalt + "$" + newHashedPassword;

                UpdateUserDTO updateUserDTO = new UpdateUserDTO(currUser);
                updateUserDTO.setPassword(finalNewPassword);

                usersRepository.updatePassword(updateUserDTO);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, LocaleAlertMessages.getLocalizedMessage(AlertMessages.SUCCESSFUL_EDIT_BUNDLE));
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, LocaleAlertMessages.getLocalizedMessage(AlertMessages.WEAK_PASSWORD));
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, LocaleAlertMessages.getLocalizedMessage(AlertMessages.NO_MATCH));
            oldPasswordField.clear();
            newPasswordField.clear();
            passwordHintLabel.setStyle("-fx-text-fill: black;");
            passwordHintLabel.setText(LocaleAlertMessages.getLocalizedMessage(PASSWORD_HINT));
            passMatchLabel.setStyle("-fx-text-fill: black;");
            passMatchLabel.setText(" ");
            alert.showAndWait();
        }
    }

}
