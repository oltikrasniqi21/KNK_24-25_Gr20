package Repository;

import Database.DBCustomConnector;
import models.Scholarships;

import javax.xml.transform.Result;
import java.sql.*;
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

    public Scholarships getById(int id){
        String query = "SELECT * FROM SCHOLARSHIPS WHERE ID=?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return Scholarships.getInstance(resultSet);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
