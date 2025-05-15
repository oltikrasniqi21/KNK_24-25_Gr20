package Repository;

import CreateDTO.CreateScholarshipDTO;
import UpdateDTO.UpdateScholarshipDTO;
import models.Scholarships;

import java.sql.*;

public class ScholarshipsRepository extends BaseRepository<Scholarships, CreateScholarshipDTO, UpdateScholarshipDTO>{
    //connection krijohet tek BaseRepository

    public ScholarshipsRepository(){
        super("scholarships", "scholarship_id");
    }

    public Scholarships fromResultSet(ResultSet res) throws SQLException{
        return Scholarships.getInstance(res);
    }

    @Override
    public Scholarships create(CreateScholarshipDTO create) {
        String query = """
                INSERT INTO SCHOLARSHIPS(SCHOLARSHIP_NAME, PROVIDER, AMOUNT, DEADLINE_DATE,
                    REQUIRED_GPA, REQUIRED_YEAR, REQUIRED_MAJOR) VALUES(?,?,?,?,?,?,?)
                """;
        try{
            PreparedStatement statement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,create.getScholarship_name());
            statement.setString(2,create.getProvider());
            statement.setInt(3,create.getAmount());
            statement.setDate(4, java.sql.Date.valueOf(create.getDeadline_date()));
            statement.setDouble(5,create.getRequired_gpa());
            statement.setInt(6,create.getRequired_year());
            statement.setString(7,create.getRequired_major());
            statement.execute();
            ResultSet set =statement.getGeneratedKeys();
            if(set.next()){
                int id = set.getInt(1);
                return this.getById(id);
            }
        }catch (SQLException e){
            System.out.println("Scholarship nuk u krijua!");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Scholarships update(UpdateScholarshipDTO update) {
        String query= """
                UPDATE SCHOLARSHIPS SET
                AMOUNT = ?,
                DEADLINE_DATE = ?,
                REQUIRED_GPA = ?,
                REQUIRED_YEAR = ?,
                REQUIRED_MAJOR = ?
                WHERE SCHOLARSHIP_ID = ?
                """;
        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, update.getAmount());
            statement.setDate(2, java.sql.Date.valueOf(update.getDeadline_date()));
            statement.setDouble(3, update.getRequired_gpa());
            statement.setInt(4, update.getRequired_year());
            statement.setString(5, update.getRequred_major());
            statement.setInt(6, update.getScholarship_id());
            int updateRow = statement.executeUpdate();
            if(updateRow == 1){
                return this.getById(update.getScholarship_id());
            }
        } catch (SQLException e) {
            System.out.println("Scholarship nuk u perditsua!");
            e.printStackTrace();
        }

        return null;
    }

}
