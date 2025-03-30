package Repository;

import Database.DBCustomConnector;

import java.sql.Connection;

public class UsersRepository {

    private Connection connection;
    public UsersRepository() {
        this.connection = DBCustomConnector.getConnection();
    }

}
