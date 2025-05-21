package Repository;

import CreateDTO.CreateUniversitiesDTO;
import UpdateDTO.UpdateUniversitiesDTO;
import models.Faculties;
import models.Universities;

import java.sql.*;

public class UniversitiesRepository extends BaseRepository<Universities, CreateUniversitiesDTO, UpdateUniversitiesDTO> {
    public UniversitiesRepository() {
        super("universities");
    }

    @Override
    public Universities fromResultSet(ResultSet rs) throws SQLException {
        return Universities.getInstance(rs);
    }

    @Override
    public Universities create(CreateUniversitiesDTO dto) {
        String query = """
            INSERT INTO universities(name, city, country)
            VALUES (?, ?, ?)
        """;
        try {
            PreparedStatement statement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, dto.getName());
            statement.setString(2, dto.getCity());
            statement.setString(3, dto.getCountry());

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
    public Universities update(UpdateUniversitiesDTO dto) {
        String query = "UPDATE universities SET name = ?, city = ?, country = ? WHERE id = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, dto.getName());
            statement.setString(2, dto.getCity());
            statement.setString(3, dto.getCountry());
            statement.setInt(4, dto.getUniversityId());

            int affected = statement.executeUpdate();
            if (affected == 1) {
                return this.getById(dto.getUniversityId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}