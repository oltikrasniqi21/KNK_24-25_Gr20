package UpdateDTO;

import java.sql.Date;

public class UpdateNewsDTO {
    private int newsId;
    private String title;
    private String content;
    private Integer scholarshipId;
    private Date visibleUntil;

    public UpdateNewsDTO(int newsId, String title, String content, Integer scholarshipId, Date visibleUntil) {
        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.scholarshipId = scholarshipId;
        this.visibleUntil = visibleUntil;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setScholarshipId(Integer scholarshipId) {
        this.scholarshipId = scholarshipId;
    }

    public void setVisibleUntil(Date visibleUntil) {
        this.visibleUntil = visibleUntil;
    }

    public int getNewsId() {
        return newsId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Integer getScholarshipId() {
        return scholarshipId;
    }

    public Date getVisibleUntil() {
        return visibleUntil;
    }
}
