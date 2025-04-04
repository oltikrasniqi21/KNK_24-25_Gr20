package Repository;

import Database.DBCustomConnector;
import models.Scholarships;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ScholarshipsRepository {
    private Connection connection;

    public ScholarshipsRepository(){
        this.connection = DBCustomConnector.getConnection();
    }

    public ArrayList<Scholarships> getAll(){
        ArrayList<Scholarships> scholarships = new ArrayList<>();
        String query = "SELECT * FROM SCHOLARSHIPS";

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                Scholarships scholarship = Scholarships.getInstance(ResultSet);
                scholarships.add(scholarship);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scholarships;
    }
}
