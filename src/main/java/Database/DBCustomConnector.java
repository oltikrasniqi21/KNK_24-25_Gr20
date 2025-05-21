package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCustomConnector {
    private static Connection connection = null;
    private static final String dbName = "Projekti-KNK";
    private static final String dbHost = "localhost";
    private static final String dbUser = "postgres";
    private static final String dbPassword = "1234";
    private static final String dbString = "jdbc:postgresql://" + dbHost + "/" + dbName;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(dbString, dbUser, dbPassword);
                System.out.println("Database connected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
