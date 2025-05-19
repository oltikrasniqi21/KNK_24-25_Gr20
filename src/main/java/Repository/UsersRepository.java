package Repository;

import Database.DBCustomConnector;
import UpdateDTO.UpdateUserDTO;
import models.Students;
import models.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersRepository {

    private final Connection connection;
    public UsersRepository() {
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
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return Users.getInstance(resultSet);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getHashedPassword(int id){
        String query = "SELECT password FROM users where id = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("password");
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getStudentNameById(int studentId){
        String query = "SELECT first_name, last_name FROM users WHERE id = ?";
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
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
                "s.gpa, s.year_of_study, s.university, s.faculty, s.major, s.priority " +
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

    public void updateStudentGPA(int studentId, double gpa){
        String query = "UPDATE students SET gpa = ? WHERE id = ?";
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setDouble(1,gpa);
            preparedStatement.setInt(2, studentId);
            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Users updateInfo(UpdateUserDTO updateUserDTO){
        String query = """
                UPDATE users SET
                first_name = ?,
                last_name = ?,
                email = ?,
                role = ?
                WHERE ID = ?
                """;
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, updateUserDTO.getFirstName());
            preparedStatement.setString(2, updateUserDTO.getLastName());
            preparedStatement.setString(3, updateUserDTO.getEmail());
            preparedStatement.setString(4, updateUserDTO.getRole());
            preparedStatement.setInt(5, updateUserDTO.getId());
            int updateRow = preparedStatement.executeUpdate();
            if(updateRow == 1){
                return this.getById(updateUserDTO.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Users updatePassword(UpdateUserDTO updateUserDTO){
        String query = """
                UPDATE users SET
                password = ?
                WHERE ID = ?
                """;
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1,updateUserDTO.getPassword());
            preparedStatement.setInt(2, updateUserDTO.getId());
            int updateRow = preparedStatement.executeUpdate();
            if(updateRow == 1){
                return this.getById(updateUserDTO.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
