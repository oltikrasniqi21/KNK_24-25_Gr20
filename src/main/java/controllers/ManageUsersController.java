package controllers;

import Repository.UsersRepository;
import Services.SceneManager;
import Database.DBCustomConnector;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import models.Users;
import utils.SceneLocator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ManageUsersController {
    @FXML
    private Button validateButton;

    @FXML
    private Button rejectButton;

    @FXML
    private TableView<Users> usersTable;

    @FXML
    private TableColumn<Users, Integer> userIdColumn;

    @FXML
    private TableColumn<Users, String> userNameColumn;

    @FXML
    private TableColumn<Users, String> userEmailColumn;

    @FXML
    private TableColumn<Users, String> userStatusColumn;

    @FXML
    private TableColumn<Users, Void> actionButtonColumn;

    private final UsersRepository usersRepository;

    public ManageUsersController() {
        this.usersRepository = new UsersRepository();
    }

    @FXML
    public void initialize() {
        userIdColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getUser_id()).asObject());

        userNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirst_name() + " " + cellData.getValue().getLast_name()));

        userEmailColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmail()));

        userStatusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus()));

        addActionButtonToTable();
        loadUsers();
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
                            int userId = user.getUser_id();
                            openUserPdf(userId);
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

    private void openUserPdf(int userId) {
        new Thread(() -> {

            // documentService.openStudentDocument(userId);
        }).start();
    }

    private void loadUsers() {
        try {
            List<Users> users = usersRepository.getAllStudentUsers();
            usersTable.getItems().setAll(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackClick() {
        SceneManager.getInstance().loadScene(SceneLocator.ADMIN_HOME_PAGE);
    }

    @FXML
    private void handleAddUserClick() {
        SceneManager.getInstance().loadScene(SceneLocator.CREATE_USERS_PAGE);
    }

    @FXML
    private void handleSearchClick() {
        // Implement search logic if needed
    }

    @FXML
    private void handleValidate() {
        Users selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            updateStudentStatus(selectedUser.getUser_id(), "validated");
        } else {
            showAlert("No user selected", "Please select a user to validate.");
        }
    }

    @FXML
    private void handleReject() {
        Users selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            updateStudentStatus(selectedUser.getUser_id(), "rejected");
        } else {
            showAlert("No user selected", "Please select a user to reject.");
        }
    }

    private void updateStudentStatus(int userId, String newStatus) {
        String query = "UPDATE users SET status = ? WHERE id = ?";
        Connection conn = DBCustomConnector.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, userId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Status updated to " + newStatus + ".");
                loadUsers();
            } else {
                showAlert("Update failed", "No rows were updated.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", e.getMessage());
        }
    }


    private void showAlert(String title, String message) {
        javafx.application.Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
