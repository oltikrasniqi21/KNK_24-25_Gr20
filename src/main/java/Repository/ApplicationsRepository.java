package Repository;

import CreateDTO.CreateApplicationDto;
import UpdateDTO.UpdateApplicationsDTO;
import models.Applications;

import java.sql.*;
import java.util.ArrayList;

public class ApplicationsRepository extends BaseRepository<Applications, CreateApplicationDto, UpdateApplicationsDTO>{

    public ApplicationsRepository(){
        super("applications","application_id");
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
        String query = "UPDATE APPLICATIONS SET STATUS = ? WHERE ID = ?";
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
}
