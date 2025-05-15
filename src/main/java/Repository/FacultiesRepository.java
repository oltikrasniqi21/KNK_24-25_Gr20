package Repository;

import CreateDTO.CreateFacultiesDTO;
import UpdateDTO.UpdateFacultiesDTO;
import models.Faculties;

import java.sql.*;

public class FacultiesRepository extends BaseRepository<Faculties, CreateFacultiesDTO, UpdateFacultiesDTO> {
    public FacultiesRepository() {
        super("faculties");
    }

    @Override
    public Faculties fromResultSet(ResultSet rs) throws SQLException {
        return Faculties.getInstance(rs);
    }

    @Override
    public Faculties create(CreateFacultiesDTO facultiesDTO) {
        String query = """
            INSERT INTO faculties(universityId, name)
            VALUES (?, ?)
        """;
        try {
            PreparedStatement statement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, facultiesDTO.getUniversityId());
            statement.setString(2, facultiesDTO.getName());

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
    Faculties update(UpdateFacultiesDTO facultiesDTO) {
        String query = "UPDATE faculties SET name = ? WHERE facultyId = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, facultiesDTO.getName());
            statement.setInt(2, facultiesDTO.getFacultyId());

            int affected = statement.executeUpdate();
            if (affected == 1) {
                return this.getById(facultiesDTO.getFacultyId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
