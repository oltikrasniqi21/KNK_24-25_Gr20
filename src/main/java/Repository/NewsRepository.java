package Repository;

import CreateDTO.CreateNewsDTO;
import UpdateDTO.UpdateNewsDTO;
import models.News;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsRepository extends BaseRepository<News, CreateNewsDTO, UpdateNewsDTO>{

    public NewsRepository(){ super("news","news_id");}

    @Override
    News fromResultSet(ResultSet res) throws SQLException {
        return News.getInstance(res);
    }
    @Override
    public News create(CreateNewsDTO dto) {
        String query = "INSERT INTO news(title,content,scholarship_id,posted_by,visible_until) VALUES (?, ?, ?, ?, ?) RETURNING *";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, dto.getTitle());
            stmt.setString(2, dto.getContent());
            if (dto.getScholarshipId() != null) {
                stmt.setInt(3, dto.getScholarshipId());
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }
            stmt.setInt(4, dto.getPostedBy());
            stmt.setDate(5, dto.getVisibleUntil());

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
        String query = "UPDATE news SET  title = ?, content = ?, scholarship_id = ?, visible_until = ? WHERE news_id = ? RETURNING *";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, dto.getTitle());
            stmt.setString(2, dto.getContent());
            if (dto.getScholarshipId() != null) {
                stmt.setInt(3, dto.getScholarshipId());
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }
            stmt.setDate(4, dto.getVisibleUntil());
            stmt.setInt(5, dto.getNewsId());

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
        String query = "DELETE FROM news WHERE news_id = ?";
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

