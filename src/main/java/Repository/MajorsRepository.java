package Repository;

import CreateDTO.CreateMajorsDTO;
import UpdateDTO.UpdateMajorsDTO;
import models.Majors;

import java.sql.*;

public class MajorsRepository extends BaseRepository<Majors, CreateMajorsDTO, UpdateMajorsDTO> {
    public MajorsRepository() {
        super("majors");
    }

    @Override
    public Majors fromResultSet(ResultSet rs) throws SQLException {
        return Majors.getInstance(rs);
    }

    @Override
    public Majors create(CreateMajorsDTO majorsDTO) {
        String query = """
            INSERT INTO majors(faculty_id, name)
            VALUES (?, ?)
        """;
        try {
            PreparedStatement statement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, majorsDTO.getFacultyId());
            statement.setString(2, majorsDTO.getName());

            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                return this.getById(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Majors update(UpdateMajorsDTO majorsDTO) {
        String query = "UPDATE majors SET name = ? WHERE id = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, majorsDTO.getName());
            statement.setInt(2, majorsDTO.getMajorId());

            int affected = statement.executeUpdate();
            if (affected == 1) {
                return this.getById(majorsDTO.getMajorId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
