package Repository;

import CreateDTO.CreateFeedbackDTO;
import UpdateDTO.UpdateFeedbackDTO;
import models.Feedback;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FeedbackRepository extends BaseRepository<Feedback, CreateFeedbackDTO, UpdateFeedbackDTO> {

    public FeedbackRepository() {
        super("feedback");
    }

    @Override
    Feedback fromResultSet(ResultSet res) throws SQLException {
        return Feedback.getInstance(res);
    }

    @Override
    public Feedback create(CreateFeedbackDTO dto) {
        String query = "INSERT INTO feedback(user_id, message, submitted_at, response) VALUES (?, ?, ?, ?) RETURNING *";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, dto.getUser_id());
            stmt.setString(2, dto.getMessage());
            stmt.setTimestamp(3, dto.getSubmitted_at());
            stmt.setString(4, dto.getResponse());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Feedback update(UpdateFeedbackDTO dto) {
        String query = "UPDATE feedback SET response = ? WHERE id = ? RETURNING *";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, dto.getResponse());
            stmt.setInt(2, dto.getFeedbackId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
