package Repository;

import Database.DBCustomConnector;

import java.sql.Connection;

public class ApplicationsRepository {

    private Connection connection;
    public ApplicationsRepository() {
        this.connection = DBCustomConnector.getConnection();
    }
}
