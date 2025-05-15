package controllers;

import Database.DBCustomConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Students;
import java.sql.*;

public class StudentListController {
    @FXML private TableView<Students> studentsTable;
    @FXML private TableColumn<Students, Integer> idColumn;
    @FXML private TableColumn<Students, Double> gpaColumn;
    @FXML private TableColumn<Students, Integer> yearColumn;
    @FXML private TableColumn<Students, String> universityColumn;
    @FXML private TableColumn<Students, String> facultyColumn;
    @FXML private TableColumn<Students, String> majorColumn;
    @FXML private TableColumn<Students, Integer> semesterColumn;
    @FXML private TableColumn<Students, String> priorityColumn;
    @FXML private TableColumn<Students, String> statusColumn;

    private ObservableList<Students> studentData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadStudentData();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        gpaColumn.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year_of_study"));
        universityColumn.setCellValueFactory(new PropertyValueFactory<>("university"));
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("courses_left"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadStudentData() {
        String query = "SELECT u.*, s.* FROM users u JOIN students s ON u.user_id = s.student_id WHERE u.role = 'student'";

        try (Connection conn = DBCustomConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            studentData.clear();

            while (rs.next()) {
                Students student = Students.getInstance(rs);
                studentData.add(student);
                System.out.println("Added student: " + student.getFirst_name()); // Debug
            }

            studentsTable.setItems(studentData);
            System.out.println("Total students loaded: " + studentData.size()); // Debug

        } catch (SQLException e) {
            System.err.println("Error loading students:");
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Failed to load student data");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}