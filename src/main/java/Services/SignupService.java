package Services;

import Repository.UniversitiesRepository;
import Repository.FacultiesRepository;
import Repository.MajorsRepository;
import javafx.scene.control.ComboBox;
import models.Universities;
import models.Faculties;
import models.Majors;
import utils.PasswordUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static utils.AlertMessages.showAlert;

public class SignupService {
    private final Connection connection;
    private final UniversitiesRepository universitiesRepository;
    private final FacultiesRepository facultiesRepository;
    private final MajorsRepository majorsRepository;

    private final Map<String, Integer> universityNameToId = new HashMap<>();
    private final Map<String, Integer> facultyNameToId = new HashMap<>();
    private final Map<String, Integer> majorNameToId = new HashMap<>();

    public SignupService(Connection connection) {
        this.connection = connection;
        this.universitiesRepository = new UniversitiesRepository();
        this.facultiesRepository = new FacultiesRepository();
        this.majorsRepository = new MajorsRepository();
    }

    public boolean isEmailTaken(String email) throws SQLException {
        String query = "SELECT 1 FROM users WHERE LOWER(email) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email.toLowerCase().trim());
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean isValidStudentEmail(String email) {
        String regex = "^[\\w.-]+@student\\.uni-[a-z]{2,10}\\.edu$";
        return Pattern.matches(regex, email);
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

    public int getUniversityIdByName(String name) throws Exception {
        List<Universities> universities = universitiesRepository.getAll();
        for (Universities uni : universities) {
            if (uni.getName().equals(name)) return uni.getUniversityId();
        }
        throw new Exception("University not found: " + name);
    }

    public int getFacultyIdByName(String name, int universityId) throws Exception {
        List<Faculties> faculties = facultiesRepository.getAll();
        for (Faculties fac : faculties) {
            if (fac.getName().equals(name) && fac.getUniversityId() == universityId) return fac.getFacultyId();
        }
        throw new Exception("Faculty not found: " + name);
    }

    public int getMajorIdByName(String name, int facultyId) throws Exception {
        List<Majors> majors = majorsRepository.getAll();
        for (Majors major : majors) {
            if (major.getName().equals(name) && major.getFacultyId() == facultyId) return major.getMajorId();
        }
        throw new Exception("Major not found: " + name);
    }

    public void signupStudent(String password, String firstName, String lastName, String email,
                              int yearOfStudy, String university, String faculty, String major, String documentPath) throws Exception {

        String salt = PasswordUtils.getSalt();
        String hashedPassword = PasswordUtils.hashPassword(password, salt);
        String passwordToStore = salt + "$" + hashedPassword;

        connection.setAutoCommit(false);
        try {
            String insertUserSQL = """
            INSERT INTO users (password, first_name, last_name, email, role)
            VALUES (?, ?, ?, ?, 'student')
            """;
            try (PreparedStatement userStmt = connection.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS)) {
                userStmt.setString(1, passwordToStore);
                userStmt.setString(2, firstName);
                userStmt.setString(3, lastName);
                userStmt.setString(4, email);
                userStmt.executeUpdate();

                ResultSet rs = userStmt.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);

                    String insertStudentSQL = """
                    INSERT INTO students (id, gpa, year_of_study, university, faculty, major, priority, document_path)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                   """;
                    try (PreparedStatement studentStmt = connection.prepareStatement(insertStudentSQL)) {
                        studentStmt.setInt(1, userId);
                        studentStmt.setNull(2, java.sql.Types.DOUBLE);
                        studentStmt.setInt(3, yearOfStudy);
                        studentStmt.setString(4, university);
                        studentStmt.setString(5, faculty);
                        studentStmt.setString(6, major);
                        studentStmt.setNull(7, java.sql.Types.VARCHAR);
                        studentStmt.setString(8, documentPath);
                        studentStmt.executeUpdate();
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
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
            showAlert("Error", "Failed to load universities: " + e.getMessage());
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
            majorNameToId.clear();
        } catch (Exception e) {
            showAlert("Error", "Failed to load faculties: " + e.getMessage());
        }
    }

    public void loadMajors(String selectedFaculty, ComboBox<String> majorComboBox) {
        try {
            int facultyId = facultyNameToId.get(selectedFaculty);
            List<Majors> majors = majorsRepository.getAll();
            majorComboBox.getItems().clear();
            majorNameToId.clear();
            for (Majors major : majors) {
                if (major.getFacultyId() == facultyId) {
                    majorComboBox.getItems().add(major.getName());
                    majorNameToId.put(major.getName(), major.getMajorId());
                }
            }
            majorComboBox.setValue(null);
        } catch (Exception e) {
            showAlert("Error", "Failed to load majors: " + e.getMessage());
        }
    }

}