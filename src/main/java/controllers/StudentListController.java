package controllers;

import Services.SceneManager;
import Services.StudentListService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.Students;
import utils.SceneLocator;

import java.util.List;

public class StudentListController {
    @FXML
    private TableView<Students> studentslistTable;
    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<Students, Integer> StudentIdColumn;
    @FXML
    private TableColumn<Students, String> studentNameColumn;
    @FXML
    private TableColumn<Students, Double> studentGpaColumn;
    @FXML
    private TableColumn<Students, Integer> studentYearColumn;
    @FXML
    private TableColumn<Students, String> studentUniversityColumn;
    @FXML
    private TableColumn<Students, String> studentFacultyColumn;
    @FXML
    private TableColumn<Students, String> studentMajorColumn;
    @FXML
    private TableColumn<Students, String> studentsStringTableColumnStatus;

    private StudentListService studentListService;

    public StudentListController() {
        studentListService = new StudentListService();
    }

    public void initialize() {
        setupTableColumns();
        loadStudents();
    }

    private void setupTableColumns() {
        StudentIdColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getUser_id()).asObject());

        studentNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getFirst_name() + " " + cellData.getValue().getLast_name()
                ));

        studentGpaColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getGpa()).asObject());

        studentYearColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getYear_of_study()).asObject());

        studentUniversityColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUniversity()));

        studentFacultyColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFaculty()));

        studentMajorColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMajor()));

        studentsStringTableColumnStatus.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus()));
    }

    private void loadStudents() {
        try {
            List<Students> students = studentListService.getAllStudents();
            studentslistTable.getItems().setAll(students);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadStudents();
        } else {
            List<Students> filteredStudents = studentListService.searchStudents(searchTerm);
            studentslistTable.getItems().setAll(filteredStudents);
        }
    }
}
