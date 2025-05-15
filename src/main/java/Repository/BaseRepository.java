package Repository;

import Database.DBCustomConnector;
import UpdateDTO.UpdateScholarshipDTO;
import models.Scholarships;

import java.sql.*;
import java.util.ArrayList;

abstract class BaseRepository<Model, CreateModelDto, UpdateModelDto> {
    protected Connection connection;
    private String tableName;
    private String idColName;

    public BaseRepository(String tableName, String idColName){
        this.connection = DBCustomConnector.getConnection();
        this.tableName = tableName;
        this.idColName = idColName;
    }
    abstract Model fromResultSet(ResultSet res) throws SQLException;

    public Model getById(int id){
        String query = "SELECT * FROM " + this.tableName + " WHERE " + this.idColName + " = ?";

        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                return this.fromResultSet(set);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Model> getAll(){
        ArrayList<Model> models = new ArrayList<>();
        String query = "SELECT * FROM " + this.tableName;
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                models.add(this.fromResultSet(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return models;
    }

    public boolean delete(int id){
        String query = "DELETE FROM " + this.tableName + " WHERE " + this.idColName+ " = ?";
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            return preparedStatement.executeUpdate() == 1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    abstract Model create(CreateModelDto createDto);
    abstract Model update(UpdateModelDto updateDto);
}
