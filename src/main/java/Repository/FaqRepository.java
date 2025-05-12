package Repository;
import CreateDTO.CreateFaqDTO;
import UpdateDTO.UpdateFaqDTO;
import models.Faq;

import java.sql.*;
import java.util.ArrayList;

public class FaqRepository extends BaseRepository<Faq, CreateFaqDTO, UpdateFaqDTO> {

    public FaqRepository() {
        super("faq"); // your table name
    }

    @Override
    Faq fromResultSet(ResultSet rs) throws SQLException {
        return Faq.getInstance(rs);
    }

    @Override
    public Faq create(CreateFaqDTO dto) {
        String query = "INSERT INTO faq (question, answer) VALUES (?, ?) RETURNING *";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, dto.getQuestion());
            stmt.setString(2, dto.getAnswer());

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
    public Faq update(UpdateFaqDTO dto) {
        String query = "UPDATE faq SET question = ?, answer = ? WHERE faq_id = ? RETURNING *";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, dto.getQuestion());
            stmt.setString(2, dto.getAnswer());
            stmt.setInt(3, dto.getFaqId());

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
