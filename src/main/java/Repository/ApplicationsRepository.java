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
                INSERT INTO APPLICATIONS(STUDENT_ID, SCHOLARSHIP_ID, APPLICATION_DATE, GPA, TRANSCRIPT_PATH)
                VALUES(?,?,?,?,?)
                """;
        try{
            PreparedStatement statement = this.connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, applicationsDto.getSid());
            statement.setInt(2, applicationsDto.getScid());
            statement.setDate(3, Date.valueOf(applicationsDto.getApplication_date()));
            statement.setDouble(4, applicationsDto.getGpa());
            statement.setString(5, applicationsDto.getTranscript_path());
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
        String query = "UPDATE applications SET STATUS = ? WHERE id = ?";
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
                a.gpa,
                s.priority,
                s.year_of_study,
                sc.scholarship_name,
                sc.required_gpa,
                sc.required_year,
                sc.deadline_date,
                a.status,
                a.application_date,
                a.transcript_path
                FROM applications a
                JOIN students s ON a.student_id = s.id
                JOIN users u ON s.id = u.id
                JOIN scholarships sc ON a.scholarship_id = sc.id
                WHERE a.id = ?""";

        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, applicationId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return new ApplicationsDetails(
                        resultSet.getString("student_name"),
                        resultSet.getString("email"),
                        resultSet.getDouble("gpa"),
                        resultSet.getString("priority"),
                        resultSet.getString("scholarship_name"),
                        resultSet.getDouble("required_gpa"),
                        resultSet.getInt("required_year"),
                        resultSet.getDate("deadline_date"),
                        resultSet.getString("status"),
                        resultSet.getInt("year_of_study"),
                        resultSet.getDate("application_date"),
                        resultSet.getString("transcript_path")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
