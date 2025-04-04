package Repository;

import CreateDTO.CreateApplicationDto;
import Database.DBCustomConnector;
import models.Applications;

import java.sql.*;
import java.util.ArrayList;

public class ApplicationsRepository {
    private Connection connection;

    public ApplicationsRepository() {
        this.connection = DBCustomConnector.getConnection();
    }

    public ArrayList<Applications> getAll(){
        ArrayList<Applications> applications = new ArrayList<>();
        String query = "SELECT * FROM APPLICATIONS";
        try{
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                applications.add(
                    Applications.getInstance(resultSet)
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return applications;
    }

    public Applications getById(int id){
        String query = "SELECT * FROM APPLICATIONS WHERE ID = ?";
        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return Applications.getInstance(resultSet);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public Applications create(CreateApplicationDto applicationsDto){
        String query = """
                INSERT INTO APPLICATIONS(APPLICATION_ID, STUDENT_ID, SCHOLARSHIP_ID, APPLICATION_DATE, STATUS)
                VALUES(?,?,?,?,?)
                """;
        try{
            PreparedStatement statement = this.connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,applicationsDto.getId());
            statement.setInt(2,applicationsDto.getSid());
            statement.setInt(3,applicationsDto.getScid());
//            statement.setDate(4,applicationsDto.getApplication_date());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                int id = resultSet.getInt(1);
                return this.getById(id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
