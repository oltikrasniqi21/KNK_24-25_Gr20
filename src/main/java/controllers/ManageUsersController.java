package controllers;

import Services.ManageUsersService;
import Services.SceneManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import models.Users;
import utils.SceneLocator;

import java.util.List;

public class ManageUsersController {
    @FXML private Button validateButton;
    @FXML private Button rejectButton;
    @FXML private TextField searchTextField;
    @FXML private TableView<Users> usersTable;
    @FXML private TableColumn<Users, Integer> userIdColumn;
    @FXML private TableColumn<Users, String> userNameColumn;
    @FXML private TableColumn<Users, String> userEmailColumn;
    @FXML private TableColumn<Users, String> userStatusColumn;
    @FXML private TableColumn<Users, Void> actionButtonColumn;

    private final ManageUsersService manageUsersService;

    public ManageUsersController() {
        this.manageUsersService = new ManageUsersService();
    }

    @FXML
    public void initialize() {
        setupTableColumns();
        addActionButtonToTable();
        loadUsers();
    }

    private void setupTableColumns() {
        userIdColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getUser_id()).asObject());
        userNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirst_name() + " " + cellData.getValue().getLast_name()));
        userEmailColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmail()));
        userStatusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus()));
    }

    private void addActionButtonToTable() {
        actionButtonColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Users, Void> call(final TableColumn<Users, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("View PDF");

                    {
                        btn.setStyle("-fx-background-color: #b0b0b0; -fx-text-fill: black; -fx-font-weight: bold;");
                        btn.setOnAction(event -> {
                            Users user = getTableView().getItems().get(getIndex());
                            manageUsersService.openStudentDocument(user.getUser_id());
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        });
    }

    private void loadUsers() {
        usersTable.getItems().setAll(manageUsersService.getAllStudentUsers());
    }



    @FXML
    private void handleAddUserClick() {
        SceneManager.getInstance().loadScene(SceneLocator.CREATE_USERS_PAGE);
    }

    @FXML
    private void handleSearchClick() {
        String searchTerm = searchTextField.getText().trim();
        List<Users> users = searchTerm.isEmpty()
                ? manageUsersService.getAllStudentUsers()
                : manageUsersService.searchUsers(searchTerm);
        usersTable.getItems().setAll(users);
    }

    @FXML
    private void handleValidate() {
        Users selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            if (manageUsersService.updateUserStatus(selectedUser.getUser_id(), "validated")) {
                manageUsersService.showAlert("Success", "Status updated to validated.");
                loadUsers();
            }
        } else {
            manageUsersService.showAlert("No user selected", "Please select a user to validate.");
        }
    }

    @FXML
    private void handleReject() {
        Users selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            if (manageUsersService.updateUserStatus(selectedUser.getUser_id(), "rejected")) {
                manageUsersService.showAlert("Success", "Status updated to rejected.");
                loadUsers();
            }
        } else {
            manageUsersService.showAlert("No user selected", "Please select a user to reject.");
        }
    }
}