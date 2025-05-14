package models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class News {
    private int newsId;
    private String title;
    private String content;
    private int scholarshipId;
    private int postedBy;
    private Timestamp postedAt;
    private Date visibleUntil;


    protected News(int newsId, String title, String content, int scholarshipId, int postedBy, Timestamp postedAt, Date visibleUntil) {
        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.scholarshipId = scholarshipId;
        this.postedBy = postedBy;
        this.postedAt = postedAt;
        this.visibleUntil = visibleUntil;
    }

        public static News getInstance(ResultSet rs) throws SQLException {
            int newsId = rs.getInt("news_id");
            String title = rs.getString("title");
            String content = rs.getString("content");
            int scholarshipId = rs.getInt("scholarship_id");
            if (rs.wasNull()) {
                scholarshipId = 0; // Default value for null
            }
            int postedBy = rs.getInt("posted_by");
            Timestamp postedAt = rs.getTimestamp("posted_at");
            Date visibleUntil = rs.getDate("visible_until");

            return new News(newsId, title, content, scholarshipId, postedBy, postedAt,visibleUntil);
        }

    public Date getVisibleUntil() {
        return visibleUntil;
    }

    public Timestamp getPostedAt() {
        return postedAt;
    }

    public int getPostedBy() {
        return postedBy;
    }

    public int getScholarshipId() {
        return scholarshipId;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public int getNewsId() {
        return newsId;
    }
}
