package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//lidhjen me databaze do e marrim te gatshme n
public class DBCustomConnector {
    private static Connection connection = null;
    private static final String dbName = "Projekti-KNK";
    private static final String dbHost = "localhost";
    private static final  String dbuser = "postgres";
    private static final  String dbpassword = "1234";
    private static final  String dbString = "jdbc:postgresql://"+dbHost+"/"+dbName;


    public static Connection getConnection(){
        if(connection == null){
            try{
                connection = DriverManager.getConnection(dbString, dbuser, dbpassword);
                System.out.println("succesful");
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return connection;
    }
}
