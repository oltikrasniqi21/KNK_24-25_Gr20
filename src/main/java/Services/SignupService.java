package Services;

import utils.PasswordUtils;

import java.sql.*;

public class SignupService {
    private final Connection connection;

    public SignupService(Connection connection) {
        this.connection = connection;
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

    public void signupStudent(String password, String firstName, String lastName, String email,
                              int yearOfStudy, String university, String faculty, String major, String documentPath) throws SQLException {


        String salt = PasswordUtils.getSalt();
        String hashedPassword = PasswordUtils.hashPassword(password, salt);
        String passwordToStore = salt + "$" + hashedPassword;

        connection.setAutoCommit(false);
        try {

            String insertUserSQL = "INSERT INTO users (password, first_name, last_name, email, role) VALUES (?, ?, ?, ?, 'student')";
            try (PreparedStatement userStmt = connection.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS)) {
                userStmt.setString(1, passwordToStore); // ← this is the hashed+salted password
                userStmt.setString(2, firstName);
                userStmt.setString(3, lastName);
                userStmt.setString(4, email);
                userStmt.executeUpdate();

                ResultSet rs = userStmt.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);

                    String insertStudentSQL = "INSERT INTO students (id, gpa, year_of_study, university, faculty, major, priority,document_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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

}
