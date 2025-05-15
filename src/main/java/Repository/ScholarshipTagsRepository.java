package Repository;

import CreateDTO.CreateScholarshipTagsDTO;
import UpdateDTO.UpdateScholarshipTagsDTO;
import models.ScholarshipTags;

import java.sql.*;

public class ScholarshipTagsRepository extends BaseRepository<ScholarshipTags, CreateScholarshipTagsDTO, UpdateScholarshipTagsDTO> {
    public ScholarshipTagsRepository() {
        super("scholarship_tags", "scholarship_tags_id");
    }

    @Override
    public ScholarshipTags fromResultSet(ResultSet rs) throws SQLException {
        return ScholarshipTags.getInstance(rs);
    }

    @Override
    public ScholarshipTags create(CreateScholarshipTagsDTO scholarshipTagsDTO) {
        String query = """
            INSERT INTO scholarshipTags(tagName)
            VALUES (?)
        """;
        try {
            PreparedStatement statement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, scholarshipTagsDTO.getTagName());

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
    ScholarshipTags update(UpdateScholarshipTagsDTO scholarshipTagsDTO) {
        String query = "UPDATE scholarshipTags SET tag_name = ? WHERE tagId = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, scholarshipTagsDTO.getTagName());
            statement.setInt(2, scholarshipTagsDTO.getTagId());

            int affected = statement.executeUpdate();
            if (affected == 1) {
                return this.getById(scholarshipTagsDTO.getTagId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
