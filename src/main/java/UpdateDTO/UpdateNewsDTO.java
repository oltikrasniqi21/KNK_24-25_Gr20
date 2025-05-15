package UpdateDTO;

import java.sql.Date;

public class UpdateNewsDTO {
    private int newsId;
    private String title;
    private String content;
    private Integer scholarshipTagId;

    public UpdateNewsDTO(int newsId, String title, String content, Integer scholarshipTagId) {
        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.scholarshipTagId = scholarshipTagId;
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

    public void setScholarshipTagId(Integer scholarshipTagId) {
        this.scholarshipTagId = scholarshipTagId;
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

    public Integer getScholarshipTagId() {
        return scholarshipTagId;
    }

}
