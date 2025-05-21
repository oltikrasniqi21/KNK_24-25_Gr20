package Services;

import Repository.FacultiesRepository;
import Repository.MajorsRepository;
import Repository.UniversitiesRepository;
import Repository.UsersRepository;
import javafx.scene.control.ComboBox;
import models.Faculties;
import models.Majors;
import models.Universities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.AlertMessages.ERROR;
import static utils.AlertMessages.showAlert;

public class UniversityService {
    private final UniversitiesRepository universitiesRepository;
    private final FacultiesRepository facultiesRepository;
    private final MajorsRepository majorsRepository;

    private final Map<String, Integer> universityNameToId = new HashMap<>();
    private final Map<String, Integer> facultyNameToId = new HashMap<>();

    public UniversityService(){
        this.facultiesRepository = new FacultiesRepository();
        this.universitiesRepository = new UniversitiesRepository();
        this.majorsRepository = new MajorsRepository();
    }

    public void loadUniversities(ComboBox<String> universityComboBox) {
        try {
            List<Universities> universities = universitiesRepository.getAll();
            universityComboBox.getItems().clear();
            universityNameToId.clear();
            for (Universities uni : universities) {
                universityComboBox.getItems().add(uni.getName());
                universityNameToId.put(uni.getName(), uni.getUniversityId());
            }
        } catch (Exception e) {
            showAlert(ERROR, "Failed to load universities: " + e.getMessage());
        }
    }

    public void loadFaculties(String selectedUniversity, ComboBox<String> facultyComboBox, ComboBox<String> majorComboBox) {
        try {
            int universityId = universityNameToId.get(selectedUniversity);
            List<Faculties> faculties = facultiesRepository.getAll();
            facultyComboBox.getItems().clear();
            facultyNameToId.clear();
            for (Faculties fac : faculties) {
                if (fac.getUniversityId() == universityId) {
                    facultyComboBox.getItems().add(fac.getName());
                    facultyNameToId.put(fac.getName(), fac.getFacultyId());
                }
            }
            facultyComboBox.setValue(null);
            majorComboBox.getItems().clear();
        } catch (Exception e) {
            showAlert(ERROR, "Failed to load faculties: " + e.getMessage());
        }
    }

    public void loadMajors(String selectedFaculty, ComboBox<String> majorComboBox) {
        try {
            int facultyId = facultyNameToId.get(selectedFaculty);
            List<Majors> majors = majorsRepository.getAll();
            majorComboBox.getItems().clear();
            for (var major : majors) {
                if (major.getFacultyId() == facultyId) {
                    majorComboBox.getItems().add(major.getName());
                }
            }
            majorComboBox.setValue(null);
        } catch (Exception e) {
            showAlert(ERROR, "Failed to load majors: " + e.getMessage());
        }
    }
}
