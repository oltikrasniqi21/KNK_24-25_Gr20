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
                Scholarships scholarship = Scholarships.getInstance(resultSet);
                scholarships.add(scholarship);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scholarships;
    }

    public Scholarships getById(int id) {
        String query = "SELECT * FROM SCHOLARSHIPS WHERE ID=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Scholarships.getInstance(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getScholarshipName(int scholarshipId){
        String query = "SELECT scholarship_name FROM scholarships WHERE scholarship_id = ?";
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, scholarshipId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("scholarship_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Double getScholarshipRequiredGPA(int scholarshipId){
        String query = "SELECT required_gpa FROM scholarships WHERE scholarship_id = ?";
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, scholarshipId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getDouble("required_gpa");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getScholarshipRequiredYear(int scholarshipId){
        String query = "SELECT required_gpa FROM scholarships WHERE scholarship_id = ?";
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, scholarshipId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("required_year");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getScholarshipDeadline(int scholarshipId){
        String query = "SELECT required_gpa FROM scholarships WHERE scholarship_id = ?";
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, scholarshipId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getDate("deadline_date");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
