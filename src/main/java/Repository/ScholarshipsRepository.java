package Repository;

import Database.DBCustomConnector;

import java.sql.Connection;

public class ScholarshipsRepository {

    private Connection connection;
    public ScholarshipsRepository() {
        this.connection = DBCustomConnector.getConnection();
    }
}
