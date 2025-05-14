package Repository;

import CreateDTO.CreateApplicationDto;
import UpdateDTO.UpdateApplicationsDTO;
import models.Applications;
import models.ApplicationsDetails;

import java.sql.*;
import java.util.ArrayList;

public class ApplicationsRepository extends BaseRepository<Applications, CreateApplicationDto, UpdateApplicationsDTO>{

    public ApplicationsRepository(){
        super("applications");
    }

    public Applications fromResultSet(ResultSet resultSet) throws SQLException{
        return Applications.getInstance(resultSet);
    }


    public Applications create(CreateApplicationDto applicationsDto){
        String query = """
                INSERT INTO APPLICATIONS(STUDENT_ID, SCHOLARSHIP_ID, APPLICATION_DATE, STATUS)
                VALUES(?,?,?,?)
                """;
        try{
            PreparedStatement statement = this.connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, applicationsDto.getSid());
            statement.setInt(2, applicationsDto.getScid());
            statement.setDate(3, Date.valueOf(applicationsDto.getApplication_date()));
            statement.setString(4, applicationsDto.getStatus());
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

    @Override
    public Applications update(UpdateApplicationsDTO updateDto) {
        String query = "UPDATE applications SET STATUS = ? WHERE application_id = ?";
        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, updateDto.getStatus());
            statement.setInt(2, updateDto.getId());

            int updateRecords = statement.executeUpdate();
            if(updateRecords == 1){
                return this.getById(updateDto.getId());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ApplicationsDetails getApplicationDetailsById(int applicationId){
        String query = """
                SELECT
                u.first_name || ' ' || u.last_name AS student_name,
                u.email,
                s.gpa,
                s.courses_left,
                s.priority,
                s.year_of_study,
                sc.scholarship_name,
                sc.required_gpa,
                sc.required_year,
                sc.deadline_date,
                a.status,
                a.application_date
                FROM applications a
                JOIN students s ON a.student_id = s.student_id
                JOIN users u ON s.student_id = u.user_id
                JOIN scholarships sc ON a.scholarship_id = sc.scholarship_id
                WHERE a.application_id = ?""";

        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, applicationId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return new ApplicationsDetails(
                        resultSet.getString("student_name"),
                        resultSet.getString("email"),
                        resultSet.getDouble("gpa"),
                        resultSet.getInt("courses_left"),
                        resultSet.getString("priority"),
                        resultSet.getString("scholarship_name"),
                        resultSet.getDouble("required_gpa"),
                        resultSet.getInt("required_year"),
                        resultSet.getDate("deadline_date"),
                        resultSet.getString("status"),
                        resultSet.getInt("year_of_study"),
                        resultSet.getDate("application_date")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
