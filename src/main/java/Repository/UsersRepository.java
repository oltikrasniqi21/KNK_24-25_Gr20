package Repository;

import Database.DBCustomConnector;
import models.Users;

import java.sql.*;
import java.util.ArrayList;

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
        String query = "SELECT * FROM USERS WHERE user_id = ?";
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



}
