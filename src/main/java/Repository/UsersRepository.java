package Repository;

import Database.DBCustomConnector;
import models.Users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

}
