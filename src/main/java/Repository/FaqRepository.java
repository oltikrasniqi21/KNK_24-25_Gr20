package Repository;
import CreateDTO.CreateFaqDTO;
import UpdateDTO.UpdateFaqDTO;
import UpdateDTO.UpdateScholarshipDTO;
import models.Faq;
import models.Scholarships;

import java.sql.*;
import java.util.ArrayList;

public class FaqRepository extends BaseRepository<Faq, CreateFaqDTO, UpdateFaqDTO> {

    public FaqRepository() {
        super("faq","faq_id"); // your table name
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

    public ArrayList<Faq> getAll() {
        ArrayList<Faq> faqs = new ArrayList<>();
        String query = "SELECT * FROM faq ORDER BY faq_id";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                faqs.add(fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faqs;
    }

    public boolean delete(int faqId) {
        String query = "DELETE FROM faq WHERE faq_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, faqId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addFaq(String question, String answer) {
        CreateFaqDTO dto = new CreateFaqDTO(question, answer);
        return create(dto) != null;
    }

    public boolean update(int faqId, String question, String answer) {
        UpdateFaqDTO dto = new UpdateFaqDTO(faqId, question, answer);
        return this.update(dto) != null;
    }

}
