package Services;

import Repository.UniversitiesRepository;
import Repository.FacultiesRepository;
import Repository.MajorsRepository;
import Repository.UsersRepository;
import javafx.scene.control.ComboBox;
import models.Universities;
import models.Faculties;
import models.Majors;
import utils.PasswordUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static utils.AlertMessages.ERROR;
import static utils.AlertMessages.showAlert;

public class SignupService {
    private final UniversitiesRepository universitiesRepository = new UniversitiesRepository();
    private final FacultiesRepository facultiesRepository = new FacultiesRepository();
    private final MajorsRepository majorsRepository = new MajorsRepository();
    private final UsersRepository usersRepository = new UsersRepository();

    private final Map<String, Integer> universityNameToId = new HashMap<>();
    private final Map<String, Integer> facultyNameToId = new HashMap<>();


    public boolean isValidStudentEmail(String email) {
        String regex = "^[\\w.-]+@student\\.uni-[a-z]{2,10}\\.edu$";
        return Pattern.matches(regex, email);
    }

    public boolean emailContainsNameAndSurname(String email, String firstName, String lastName) {
        if (email == null || firstName == null || lastName == null) return false;
        String emailLower = email.toLowerCase();
        return emailLower.contains(firstName.toLowerCase()) && emailLower.contains(lastName.toLowerCase());
    }

    public boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
    }

    public String saveStudentDocument(File pdfFile) throws IOException {
        if (pdfFile == null) return null;
        File uploadDir = new File("uploads");
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new IOException("Failed to create upload directory.");
        }
        File dest = new File(uploadDir, pdfFile.getName());
        Files.copy(pdfFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return dest.getAbsolutePath();
    }

    public void signupStudent(String password, String firstName, String lastName, String email,
                              int yearOfStudy, String university, String faculty, String major, String documentPath) throws Exception {

        String salt = PasswordUtils.getSalt();
        String hashedPassword = PasswordUtils.hashPassword(password, salt);
        String passwordToStore = salt + "$" + hashedPassword;

        usersRepository.signupStudent(passwordToStore, firstName, lastName, email, yearOfStudy, university, faculty, major, documentPath);
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