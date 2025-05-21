package models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class News {
    private int newsId;
    private String title;
    private String content;
    private int scholarshipTagId;
    private int postedBy;
    private Timestamp postedAt;
    private String summary;
    private String imagePath;


    protected News(int newsId, String title, String content, int scholarshipTagId, int postedBy,
                   Timestamp postedAt, String summary, String imagePath) {
        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.scholarshipTagId = scholarshipTagId;
        this.postedBy = postedBy;
        this.postedAt = postedAt;
        this.summary = summary;
        this.imagePath=imagePath;
    }

        public static News getInstance(ResultSet rs) throws SQLException {
            int newsId = rs.getInt("id");
            String title = rs.getString("title");
            String content = rs.getString("content");
            int scholarshipTagId = rs.getInt("scholarship_tag_id");
            String summary = rs.getString("summary");
            String imagePath = rs.getString("imagePath");
            int postedBy = rs.getInt("posted_by");
            Timestamp postedAt = rs.getTimestamp("posted_at");

            return new News(newsId, title, content, scholarshipTagId, postedBy, postedAt, summary, imagePath);
        }


    public Timestamp getPostedAt() {
        return postedAt;
    }

    public int getPostedBy() {
        return postedBy;
    }

    public int getScholarshipTagId() {
        return scholarshipTagId;
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

    public String getSummary(){return summary;};

    public String getImagePath(){return imagePath;}
}
