package Repository;

import CreateDTO.CreateNewsDTO;
import UpdateDTO.UpdateNewsDTO;
import models.News;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsRepository extends BaseRepository<News, CreateNewsDTO, UpdateNewsDTO>{

    public NewsRepository(){ super("news");}

    @Override
    News fromResultSet(ResultSet res) throws SQLException {
        return News.getInstance(res);
    }
    @Override
    public News create(CreateNewsDTO dto) {
        String query = "INSERT INTO news(title,scholarship_tag_id,posted_by, summary, imagePath) VALUES (?, ?, ?, ?, ?) RETURNING *";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, dto.getTitle());
            if (dto.getScholarshipTagId() != null) {
                stmt.setInt(2, dto.getScholarshipTagId());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setInt(3, dto.getPostedBy());
            stmt.setString(4, dto.getSummary());
            stmt.setString(5, dto.getImagePath());

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
    public News update(UpdateNewsDTO dto) {
        String query = "UPDATE news SET  title = ?, scholarship_tag_id = ?, summary = ?, imagePath=? WHERE id = ? RETURNING *";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, dto.getTitle());
            if (dto.getScholarshipTagId() != null) {
                stmt.setInt(2, dto.getScholarshipTagId());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setString(3,dto.getSummary());
            stmt.setInt(4, dto.getNewsId());
            stmt.setString(5, dto.getImagePath());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<News> getAllNews() {
        List<News> newsList = new ArrayList<>();
        String query = "SELECT * FROM news";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                News news = fromResultSet(rs);
                newsList.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    public boolean delete(int newsId) {
        String query = "DELETE FROM news WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, newsId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}

