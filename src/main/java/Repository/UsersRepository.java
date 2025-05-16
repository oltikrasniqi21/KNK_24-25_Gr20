package Repository;

import Database.DBCustomConnector;
import models.Students;
import models.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersRepository {

    private Connection connection;
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
            "WHERE u.role = 'student'";
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

}
