package controllers;

import CreateDTO.CreateFacultiesDTO;
import CreateDTO.CreateMajorsDTO;
import CreateDTO.CreateUniversitiesDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Universities;
import Services.ManageUniversitiesService;

import java.util.*;

import static utils.AlertMessages.ERROR;
import static utils.AlertMessages.showAlert;

public class ManageUniversitiesController {

    private final ManageUniversitiesService universityService = new ManageUniversitiesService();

    private final Map<String, Integer> universityNameToId = new HashMap<>();
    private final Map<String, Integer> facultyNameToId = new HashMap<>();

    private final ObservableList<UniversityItem> universityItems = FXCollections.observableArrayList();
    private final ObservableList<FacultyItem> facultyItems = FXCollections.observableArrayList();
    private final ObservableList<MajorItem> majorItems = FXCollections.observableArrayList();

    @FXML private TableView<UniversityItem> universityTable;
    @FXML private TableColumn<UniversityItem, String> universityNameColumn;
    @FXML private TableColumn<UniversityItem, String> universityCityColumn;
    @FXML private TableColumn<UniversityItem, String> universityCountryColumn;
    @FXML private TextField universityNameField;
    @FXML private TextField universityCityField;
    @FXML private TextField universityCountryField;

    @FXML private ComboBox<String> universityFilterForFaculty;
    @FXML private TableView<FacultyItem> facultyTable;
    @FXML private TextField facultyNameField;
    @FXML private TableColumn<FacultyItem, String> facultyNameColumn;

    @FXML private ComboBox<String> universityFilterForMajor;
    @FXML private ComboBox<String> facultyFilterForMajor;
    @FXML private TableView<MajorItem> majorTable;
    @FXML private TableColumn<MajorItem, String> majorNameColumn;
    @FXML private TextField majorNameField;

    public static class UniversityItem {
        private final int id;
        private final String name;
        private final String city;
        private final String country;
        public UniversityItem(int id, String name, String city, String country) {
            this.id = id; this.name = name; this.city = city; this.country = country;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getCity() { return city; }
        public String getCountry() { return country; }
    }

    public static class FacultyItem {
        private final int id;
        private final String name;
        private final int universityId;
        public FacultyItem(int id, String name, int universityId) {
            this.id = id; this.name = name; this.universityId = universityId;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public int getUniversityId() { return universityId; }
    }

    public static class MajorItem {
        private final int id;
        private final String name;
        private final int facultyId;
        public MajorItem(int id, String name, int facultyId) {
            this.id = id; this.name = name; this.facultyId = facultyId;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public int getFacultyId() { return facultyId; }
    }

    @FXML
    public void initialize() {
        universityNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        universityCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        universityCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        universityTable.setItems(universityItems);

        facultyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        facultyTable.setItems(facultyItems);

        majorNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        majorTable.setItems(majorItems);

        loadUniversities();

        universityFilterForFaculty.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) loadFacultiesByUniversity(universityNameToId.get(newVal));
        });

        universityFilterForMajor.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) loadFacultiesDropdown(universityNameToId.get(newVal));
        });

        facultyFilterForMajor.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) loadMajorsByFaculty(facultyNameToId.get(newVal));
        });

        universityTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                universityNameField.setText(newSel.getName());
                universityCityField.setText(newSel.getCity());
                universityCountryField.setText(newSel.getCountry());
            }
        });

        facultyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) facultyNameField.setText(newSel.getName());
        });

        majorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) majorNameField.setText(newSel.getName());
        });
    }

    private void loadUniversities() {
        try {
            List<Universities> universities = universityService.getAllUniversities();
            universityItems.clear();
            universityNameToId.clear();
            ObservableList<String> universityNames = FXCollections.observableArrayList();

            for (var uni : universities) {
                universityItems.add(new UniversityItem(uni.getUniversityId(), uni.getName(), uni.getCity(), uni.getCountry()));
                universityNames.add(uni.getName());
                universityNameToId.put(uni.getName(), uni.getUniversityId());
            }

            universityFilterForFaculty.setItems(universityNames);
            universityFilterForMajor.setItems(universityNames);

            if (!universityNames.isEmpty()) {
                universityFilterForFaculty.getSelectionModel().selectFirst();
                universityFilterForMajor.getSelectionModel().selectFirst();
            }
        } catch (Exception e) {
            showAlert(ERROR, "Error loading universities: " + e.getMessage());
        }
    }

    @FXML
    public void onAddUniversity(ActionEvent event) {
        String name = universityNameField.getText().trim();
        String city = universityCityField.getText().trim();
        String country = universityCountryField.getText().trim();

        if (name.isEmpty() || city.isEmpty() || country.isEmpty()) {
            showAlert(ERROR, "Please fill in all university fields.");
            return;
        }

        try {
            var dto = new CreateUniversitiesDTO(name, city, country);
            var newUni = universityService.createUniversity(dto);

            if (newUni != null) {
                universityNameField.clear();
                universityCityField.clear();
                universityCountryField.clear();
                loadUniversities();
                universityFilterForFaculty.getSelectionModel().select(newUni.getName());
                universityFilterForMajor.getSelectionModel().select(newUni.getName());
            } else {
                showAlert(ERROR, "Failed to add university.");
            }
        } catch (Exception e) {
            showAlert(ERROR, "Error adding university: " + e.getMessage());
        }
    }

    @FXML
    public void onDeleteUniversity(ActionEvent event) {
        UniversityItem selected = universityTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(ERROR, "Please select a university to delete.");
            return;
        }

        try {
            boolean deleted = universityService.deleteUniversity(selected.getId());
            if (deleted) {
                universityNameField.clear();
                universityCityField.clear();
                universityCountryField.clear();
                loadUniversities();
            } else {
                showAlert(ERROR, "Failed to delete university.");
            }
        } catch (Exception e) {
            showAlert(ERROR, "Error deleting university: " + e.getMessage());
        }
    }

    private void loadFacultiesByUniversity(int universityId) {
        try {
            var faculties = universityService.getFacultiesByUniversityId(universityId);
            facultyItems.clear();
            facultyNameToId.clear();
            ObservableList<String> facultyNames = FXCollections.observableArrayList();

            for (var faculty : faculties) {
                facultyItems.add(new FacultyItem(faculty.getFacultyId(), faculty.getName(), faculty.getUniversityId()));
                facultyNames.add(faculty.getName());
                facultyNameToId.put(faculty.getName(), faculty.getFacultyId());
            }

            facultyTable.setItems(facultyItems);
            facultyFilterForMajor.setItems(facultyNames);

            if (!facultyNames.isEmpty()) {
                facultyFilterForMajor.getSelectionModel().selectFirst();
            } else {
                majorItems.clear();
                majorTable.setItems(majorItems);
            }
        } catch (Exception e) {
            showAlert(ERROR, "Error loading faculties: " + e.getMessage());
        }
    }

    private void loadFacultiesDropdown(int universityId) {
        try {
            var faculties = universityService.getFacultiesByUniversityId(universityId);
            facultyNameToId.clear();
            ObservableList<String> facultyNames = FXCollections.observableArrayList();

            for (var faculty : faculties) {
                facultyNames.add(faculty.getName());
                facultyNameToId.put(faculty.getName(), faculty.getFacultyId());
            }

            facultyFilterForMajor.setItems(facultyNames);

            if (!facultyNames.isEmpty()) {
                facultyFilterForMajor.getSelectionModel().selectFirst();
            } else {
                majorItems.clear();
                majorTable.setItems(majorItems);
            }
        } catch (Exception e) {
            showAlert(ERROR, "Error loading faculties: " + e.getMessage());
        }
    }

    private void loadMajorsByFaculty(int facultyId) {
        try {
            var majors = universityService.getMajorsByFacultyId(facultyId);
            majorItems.clear();

            for (var major : majors) {
                majorItems.add(new MajorItem(major.getMajorId(), major.getName(), major.getFacultyId()));
            }

            majorTable.setItems(majorItems);
        } catch (Exception e) {
            showAlert(ERROR, "Error loading majors: " + e.getMessage());
        }
    }

    @FXML
    public void onAddFaculty(ActionEvent event) {
        String facultyName = facultyNameField.getText().trim();
        String selectedUniversity = universityFilterForFaculty.getValue();

        if (facultyName.isEmpty() || selectedUniversity == null) {
            showAlert(ERROR, "Please enter a faculty name and select a university.");
            return;
        }

        int universityId = universityNameToId.get(selectedUniversity);

        try {
            var dto = new CreateFacultiesDTO(universityId, facultyName);
            var newFaculty = universityService.createFaculty(dto);

            if (newFaculty != null) {
                facultyNameField.clear();
                loadFacultiesByUniversity(universityId);
                loadFacultiesDropdown(universityId);
                facultyFilterForMajor.getSelectionModel().select(newFaculty.getName());
            } else {
                showAlert(ERROR, "Failed to add faculty.");
            }
        } catch (Exception e) {
            showAlert(ERROR, "Error adding faculty: " + e.getMessage());
        }
    }

    @FXML
    public void onDeleteFaculty(ActionEvent event) {
        FacultyItem selected = facultyTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(ERROR, "Please select a faculty to delete.");
            return;
        }

        try {
            boolean deleted = universityService.deleteFaculty(selected.getId());

            if (deleted) {
                facultyNameField.clear();
                loadFacultiesByUniversity(selected.getUniversityId());
                loadFacultiesDropdown(selected.getUniversityId());
            } else {
                showAlert(ERROR, "Failed to delete faculty.");
            }
        } catch (Exception e) {
            showAlert(ERROR, "Error deleting faculty: " + e.getMessage());
        }
    }

    @FXML
    public void onAddMajor(ActionEvent event) {
        String majorName = majorNameField.getText().trim();
        String selectedFaculty = facultyFilterForMajor.getValue();

        if (majorName.isEmpty() || selectedFaculty == null) {
            showAlert(ERROR, "Please enter a major name and select a faculty.");
            return;
        }

        int facultyId = facultyNameToId.get(selectedFaculty);

        try {
            var dto = new CreateMajorsDTO(facultyId, majorName);
            var newMajor = universityService.createMajor(dto);

            if (newMajor != null) {
                majorNameField.clear();
                loadMajorsByFaculty(facultyId);
            } else {
                showAlert(ERROR, "Failed to add major.");
            }
        } catch (Exception e) {
            showAlert(ERROR, "Error adding major: " + e.getMessage());
        }
    }

    @FXML
    public void onDeleteMajor(ActionEvent event) {
        MajorItem selected = majorTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(ERROR, "Please select a major to delete.");
            return;
        }

        try {
            boolean deleted = universityService.deleteMajor(selected.getId());

            if (deleted) {
                majorNameField.clear();
                loadMajorsByFaculty(selected.getFacultyId());
            } else {
                showAlert(ERROR, "Failed to delete major.");
            }
        } catch (Exception e) {
            showAlert(ERROR, "Error deleting major: " + e.getMessage());
        }
    }
}
