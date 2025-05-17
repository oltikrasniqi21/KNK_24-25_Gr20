package controllers;

import CreateDTO.CreateFacultiesDTO;
import CreateDTO.CreateMajorsDTO;
import CreateDTO.CreateUniversitiesDTO;
import Repository.FacultiesRepository;
import Repository.MajorsRepository;
import Repository.UniversitiesRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Faculties;
import models.Majors;
import models.Universities;

import java.net.URL;
import java.util.*;

public class ManageUniversitiesController implements Initializable {
    private final UniversitiesRepository universitiesRepository = new UniversitiesRepository();
    private final FacultiesRepository facultiesRepository = new FacultiesRepository();
    private final MajorsRepository majorsRepository = new MajorsRepository();

    private final Map<String, Integer> universityNameToId = new HashMap<>();
    private final Map<String, Integer> facultyNameToId = new HashMap<>();
    private final Map<Integer, String> universityIdToName = new HashMap<>();
    private final Map<Integer, String> facultyIdToName = new HashMap<>();

    private final ObservableList<UniversityItem> universityItems = FXCollections.observableArrayList();
    private final ObservableList<FacultyItem> facultyItems = FXCollections.observableArrayList();
    private final ObservableList<MajorItem> majorItems = FXCollections.observableArrayList();

    @FXML
    private TableView<UniversityItem> universityTable;
    @FXML
    private TableColumn<UniversityItem, String> universityNameColumn;
    @FXML
    private TableColumn<UniversityItem, String> universityCityColumn;
    @FXML
    private TableColumn<UniversityItem, String> universityCountryColumn;
    @FXML
    private TextField universityNameField;
    @FXML
    private TextField universityCityField;
    @FXML
    private TextField universityCountryField;

    @FXML
    private ComboBox<String> universityFilterForFaculty;
    @FXML
    private TableView<FacultyItem> facultyTable;
    @FXML
    private TextField facultyNameField;
    @FXML
    private TableColumn<FacultyItem, String> facultyNameColumn;

    @FXML
    private ComboBox<String> universityFilterForMajor;
    @FXML
    private ComboBox<String> facultyFilterForMajor;
    @FXML
    private TableView<MajorItem> majorTable;
    @FXML
    private TableColumn<MajorItem, String> majorNameColumn;
    @FXML
    private TextField majorNameField;

    // Model classes for TableView
// Place these as static inner classes in ManageUniversitiesController

    public static class UniversityItem {
        private final int id;
        private final String name;
        private final String city;
        private final String country;

        public UniversityItem(int id, String name, String city, String country) {
            this.id = id;
            this.name = name;
            this.city = city;
            this.country = country;
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
            this.id = id;
            this.name = name;
            this.universityId = universityId;
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
            this.id = id;
            this.name = name;
            this.facultyId = facultyId;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public int getFacultyId() { return facultyId; }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // University TableView columns
        universityNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        universityCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        universityCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        universityTable.setItems(universityItems);

        // Faculty TableView columns
        facultyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        facultyTable.setItems(facultyItems);

        // Major TableView columns
        majorNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        majorTable.setItems(majorItems);

        loadUniversities();

        // Listeners for ComboBoxes
        universityFilterForFaculty.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) loadFacultiesByUniversity(universityNameToId.get(newVal));
        });
        universityFilterForMajor.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) loadFacultiesDropdown(universityNameToId.get(newVal));
        });
        facultyFilterForMajor.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) loadMajorsByFaculty(facultyNameToId.get(newVal));
        });

        // Table selection listeners
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
            List<Universities> universities = universitiesRepository.getAll();
            universityItems.clear();
            universityNameToId.clear();
            universityIdToName.clear();
            ObservableList<String> universityNames = FXCollections.observableArrayList();

            for (var uni : universities) {
                universityItems.add(new UniversityItem(uni.getUniversityId(), uni.getName(), uni.getCity(), uni.getCountry()));
                universityNames.add(uni.getName());
                universityNameToId.put(uni.getName(), uni.getUniversityId());
                universityIdToName.put(uni.getUniversityId(), uni.getName());
            }

            universityFilterForFaculty.setItems(universityNames);
            universityFilterForMajor.setItems(universityNames);

            if (!universityNames.isEmpty()) {
                universityFilterForFaculty.getSelectionModel().selectFirst();
                universityFilterForMajor.getSelectionModel().selectFirst();
            }
        } catch (Exception e) {
            showAlert("Error loading universities: " + e.getMessage());
        }
    }

    @FXML
    public void onAddUniversity(ActionEvent event) {
        String name = universityNameField.getText().trim();
        String city = universityCityField.getText().trim();
        String country = universityCountryField.getText().trim();

        if (name.isEmpty() || city.isEmpty() || country.isEmpty()) {
            showAlert("Please fill in all university fields.");
            return;
        }

        try {
            CreateUniversitiesDTO dto = new CreateUniversitiesDTO(name, city, country);
            Universities newUni = universitiesRepository.create(dto);
            if (newUni != null) {
                universityNameField.clear();
                universityCityField.clear();
                universityCountryField.clear();
                loadUniversities();
            } else {
                showAlert("Failed to add university.");
            }
        } catch (Exception e) {
            showAlert("Error adding university: " + e.getMessage());
        }
    }

    @FXML
    public void onDeleteUniversity(ActionEvent event) {
        UniversityItem selected = universityTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a university to delete.");
            return;
        }
        try {
            boolean deleted = universitiesRepository.delete(selected.getId());
            if (deleted) {
                universityNameField.clear();
                universityCityField.clear();
                universityCountryField.clear();
                loadUniversities();
            } else {
                showAlert("Failed to delete university. It may have faculties.");
            }
        } catch (Exception e) {
            showAlert("Error deleting university: " + e.getMessage());
        }
    }

    private void loadFacultiesByUniversity(int universityId) {
        try {
            List<Faculties> faculties = facultiesRepository.getAll().stream()
                    .filter(f -> f.getUniversityId() == universityId)
                    .toList();

            facultyItems.clear();
            facultyNameToId.clear();
            facultyIdToName.clear();

            for (var faculty : faculties) {
                facultyItems.add(new FacultyItem(
                        faculty.getFacultyId(),
                        faculty.getName(),
                        faculty.getUniversityId()
                ));
                facultyNameToId.put(faculty.getName(), faculty.getFacultyId());
                facultyIdToName.put(faculty.getFacultyId(), faculty.getName());
            }

            facultyTable.setItems(facultyItems);
        } catch (Exception e) {
            showAlert("Error loading faculties: " + e.getMessage());
        }
    }

    private void loadFacultiesDropdown(int universityId) {
        try {
            List<Faculties> faculties = facultiesRepository.getAll().stream()
                    .filter(f -> f.getUniversityId() == universityId)
                    .toList();

            ObservableList<String> facultyNames = FXCollections.observableArrayList();
            facultyNameToId.clear();

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
            showAlert("Error loading faculties: " + e.getMessage());
        }
    }

    private void loadMajorsByFaculty(int facultyId) {
        try {
            List<Majors> majors = majorsRepository.getAll().stream()
                    .filter(m -> m.getFacultyId() == facultyId)
                    .toList();

            majorItems.clear();

            for (Majors major : majors) {
                majorItems.add(new MajorItem(
                        major.getMajorId(),
                        major.getName(),
                        major.getFacultyId()
                ));
            }

            majorTable.setItems(majorItems);
        } catch (Exception e) {
            showAlert("Error loading majors: " + e.getMessage());
        }
    }

    @FXML
    public void onAddFaculty(ActionEvent event) {
        String facultyName = facultyNameField.getText().trim();
        String selectedUniversity = universityFilterForFaculty.getValue();

        if (facultyName.isEmpty() || selectedUniversity == null) {
            showAlert("Please enter a faculty name and select a university.");
            return;
        }

        int universityId = universityNameToId.get(selectedUniversity);

        try {
            CreateFacultiesDTO dto = new CreateFacultiesDTO(universityId, facultyName);
            Faculties newFaculty = facultiesRepository.create(dto);

            if (newFaculty != null) {
                facultyNameField.clear();
                loadFacultiesByUniversity(universityId);
            } else {
                showAlert("Failed to add faculty.");
            }
        } catch (Exception e) {
            showAlert("Error adding faculty: " + e.getMessage());
        }
    }

    @FXML
    public void onDeleteFaculty(ActionEvent actionEvent) {
        FacultyItem selectedFaculty = facultyTable.getSelectionModel().getSelectedItem();

        if (selectedFaculty == null) {
            showAlert("Please select a faculty to delete.");
            return;
        }

        try {
            boolean deleted = facultiesRepository.delete(selectedFaculty.getId());

            if (deleted) {
                facultyNameField.clear();
                loadFacultiesByUniversity(selectedFaculty.getUniversityId());
            } else {
                showAlert("Failed to delete faculty. It may be referenced by majors.");
            }
        } catch (Exception e) {
            showAlert("Error deleting faculty: " + e.getMessage());
        }
    }

    @FXML
    public void onAddMajor(ActionEvent actionEvent) {
        String majorName = majorNameField.getText().trim();
        String selectedFaculty = facultyFilterForMajor.getValue();

        if (majorName.isEmpty() || selectedFaculty == null) {
            showAlert("Please enter a major name and select a faculty.");
            return;
        }

        int facultyId = facultyNameToId.get(selectedFaculty);

        try {
            CreateMajorsDTO dto = new CreateMajorsDTO(facultyId, majorName);
            Majors newMajor = majorsRepository.create(dto);

            if (newMajor != null) {
                majorNameField.clear();
                loadMajorsByFaculty(facultyId);
            } else {
                showAlert("Failed to add major.");
            }
        } catch (Exception e) {
            showAlert("Error adding major: " + e.getMessage());
        }
    }

    @FXML
    public void onDeleteMajor(ActionEvent actionEvent) {
        MajorItem selectedMajor = majorTable.getSelectionModel().getSelectedItem();

        if (selectedMajor == null) {
            showAlert("Please select a major to delete.");
            return;
        }

        try {
            boolean deleted = majorsRepository.delete(selectedMajor.getId());

            if (deleted) {
                majorNameField.clear();
                loadMajorsByFaculty(selectedMajor.getFacultyId());
            } else {
                showAlert("Failed to delete major.");
            }
        } catch (Exception e) {
            showAlert("Error deleting major: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}