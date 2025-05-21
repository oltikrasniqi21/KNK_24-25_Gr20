package Repository;

import Database.DBCustomConnector;
import UpdateDTO.UpdateUserDTO;
import models.Students;
import models.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersRepository{

    private final Connection connection;

    public UsersRepository() {
        super();
        this.connection = DBCustomConnector.getConnection();
    }

    public ArrayList<Users> getAll() {
        String query = "SELECT * FROM USERS";
        ArrayList<Users> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                users.add(Users.getInstance(resultSet));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return users;
        }
    }

    public Users getById(int id) {
        String query = "SELECT * FROM USERS WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Users.getInstance(resultSet);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getHashedPassword(int id) {
        String query = "SELECT password FROM users where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("password");
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getStudentNameById(int studentId) {
        String query = "SELECT first_name, last_name FROM users WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                return firstName + " " + lastName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Students> getAllStudents() {
        String query = "SELECT u.id, u.password, u.first_name, u.last_name, u.email, u.role, u.status, " +
                "s.gpa, s.year_of_study, s.university, s.faculty, s.major " +
                "FROM users u JOIN students s ON u.id = s.id " +
                "WHERE u.role = 'student' AND u.status = 'validated'";

        List<Students> students = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                students.add(Students.getInstance(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }


    public List<Students> searchStudents(String searchTerm) {
        String query = "SELECT u.id, u.password, u.first_name, u.last_name, u.email, u.role, u.status, " +
                "s.gpa, s.year_of_study, s.university, s.faculty, s.major, s.priority " +
                "FROM users u JOIN students s ON u.id = s.id " +
                "WHERE u.role = 'student' AND " +
                "(LOWER(u.first_name) LIKE ? OR " +
                "LOWER(u.last_name) LIKE ? OR " +
                "LOWER(s.university) LIKE ? OR " +
                "LOWER(s.faculty) LIKE ? OR " +
                "LOWER(s.major) LIKE ?)";

        List<Students> students = new ArrayList<>();
        String likeTerm = "%" + searchTerm.toLowerCase() + "%";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 1; i <= 5; i++) {
                preparedStatement.setString(i, likeTerm);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(Students.getInstance(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Users> getAllStudentUsers() {
        String query = "SELECT * FROM users WHERE role = 'student' ORDER BY id";
        List<Users> users = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(Users.getInstance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    public List<Users> searchUsers(String searchTerm) {
        String query = "SELECT * FROM users WHERE role = 'student' AND (" +
                "LOWER(first_name) LIKE ? OR " +
                "LOWER(last_name) LIKE ? OR " +
                "LOWER(email) LIKE ?)";

        List<Users> users = new ArrayList<>();
        String likeTerm = "%" + searchTerm.toLowerCase() + "%";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, likeTerm);
            ps.setString(2, likeTerm);
            ps.setString(3, likeTerm);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(Users.getInstance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void updateStudentGPA(int studentId, double gpa) {
        String query = "UPDATE students SET gpa = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setDouble(1, gpa);
            preparedStatement.setInt(2, studentId);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Users updateInfo(UpdateUserDTO updateUserDTO) {
        String query = """
                UPDATE users SET
                first_name = ?,
                last_name = ?,
                email = ?,
                role = ?
                WHERE ID = ?
                """;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, updateUserDTO.getFirstName());
            preparedStatement.setString(2, updateUserDTO.getLastName());
            preparedStatement.setString(3, updateUserDTO.getEmail());
            preparedStatement.setString(4, updateUserDTO.getRole());
            preparedStatement.setInt(5, updateUserDTO.getId());
            int updateRow = preparedStatement.executeUpdate();
            if (updateRow == 1) {
                return this.getById(updateUserDTO.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Users updatePassword(UpdateUserDTO updateUserDTO) {
        String query = """
                UPDATE users SET
                password = ?
                WHERE ID = ?
                """;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, updateUserDTO.getPassword());
            preparedStatement.setInt(2, updateUserDTO.getId());
            int updateRow = preparedStatement.executeUpdate();
            if (updateRow == 1) {
                return this.getById(updateUserDTO.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void signupStudent(String passwordToStore, String firstName, String lastName, String email,
                              int yearOfStudy, String university, String faculty, String major, String documentPath) throws Exception {
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
                        INSERT INTO students (id, gpa, year_of_study, university, faculty, major, document_path)
                        VALUES (?, ?, ?, ?, ?, ?, ?)
                        """;
                    try (PreparedStatement studentStmt = connection.prepareStatement(insertStudentSQL)) {
                        studentStmt.setInt(1, userId);
                        studentStmt.setNull(2, java.sql.Types.DOUBLE);
                        studentStmt.setInt(3, yearOfStudy);
                        studentStmt.setString(4, university);
                        studentStmt.setString(5, faculty);
                        studentStmt.setString(6, major);
                        studentStmt.setString(7, documentPath);
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

    public Map<String, Integer> countUsersByStatus() {
        String query = """
                SELECT status, COUNT(*) AS user_count
                FROM users
                GROUP BY status
                """;

        Map<String, Integer> statusCounts = new HashMap<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String status = resultSet.getString("status");
                int count = resultSet.getInt("user_count");
                statusCounts.put(status, count);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statusCounts;
    }

    public Map<String, Integer> countUsersByRole() {
        String query = """
                SELECT role, COUNT(*) AS role_count
                FROM users
                GROUP BY role
                """;
        Map<String, Integer> roleCounts = new HashMap<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String role = resultSet.getString("role");
                int count = resultSet.getInt("role_count");
                roleCounts.put(role, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleCounts;
    }

    public int getUserCount() {
        String query = """
                SELECT COUNT(*) AS total_users FROM users;
                """;
        int totalUsers = 0;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()){

            while(resultSet.next()) {
                totalUsers = resultSet.getInt("total_users");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return totalUsers;
    }

    public Map<String, Integer> countStudentsByUniversity() {
        String query = """
            SELECT university, COUNT(*) AS student_count
            FROM students
            GROUP BY university
            """;

        Map<String, Integer> universityCounts = new HashMap<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String university = resultSet.getString("university");
                int count = resultSet.getInt("student_count");
                universityCounts.put(university, count);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return universityCounts;
    }

    public boolean isValid(int studentId){
        String query = "SELECT status FROM users WHERE id = ?";

        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1,studentId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                String status = resultSet.getString("status");
                return "validated".equalsIgnoreCase(status);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Users findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Users.getInstance(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getUserIdByEmail(String email) {
        String query = "SELECT id FROM users WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int updateUserStatus(int userId, String newStatus) {
        String query = "UPDATE users SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newStatus);
            ps.setInt(2, userId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public boolean emailExists(String email) {
        String query = "SELECT 1 FROM users WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createAdminUser(String passwordToStore, String firstName, String lastName, String email, String admin, Object o) {
        String query = "INSERT INTO users (password, first_name, last_name, email, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, passwordToStore);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, email);
            ps.setString(5, admin); // assuming 'admin' is the role string
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getStudentDocumentPathById(int studentId) {
        String query = "SELECT document_path FROM students WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("document_path");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
