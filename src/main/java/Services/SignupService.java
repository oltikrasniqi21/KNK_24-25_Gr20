package Services;

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
                              double gpa, int yearOfStudy, String university,
                              String faculty, String major, String priority) throws SQLException {

        connection.setAutoCommit(false);
        try {
            String insertUserSQL = "INSERT INTO users (password, first_name, last_name, email, role) VALUES (?, ?, ?, ?, 'student')";
            try (PreparedStatement userStmt = connection.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS)) {
                userStmt.setString(1, password);
                userStmt.setString(2, firstName);
                userStmt.setString(3, lastName);
                userStmt.setString(4, email);
                userStmt.executeUpdate();

                ResultSet rs = userStmt.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);

                    String insertStudentSQL = "INSERT INTO students (student_id, gpa, year_of_study, university, faculty, major, priority) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement studentStmt = connection.prepareStatement(insertStudentSQL)) {
                        studentStmt.setInt(1, userId);
                        studentStmt.setDouble(2, gpa);
                        studentStmt.setInt(3, yearOfStudy);
                        studentStmt.setString(4, university);
                        studentStmt.setString(5, faculty);
                        studentStmt.setString(6, major);
                        studentStmt.setString(7, priority);
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
