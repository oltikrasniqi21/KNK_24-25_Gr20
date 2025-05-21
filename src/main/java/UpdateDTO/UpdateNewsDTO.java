package UpdateDTO;


public class UpdateNewsDTO {
    private int newsId;
    private String title;
    private String content;
    private Integer scholarshipTagId;
    private String summary;
    private String imagePath;

    public UpdateNewsDTO(int newsId, String title, String content,
                         Integer scholarshipTagId, String summary, String imagePath) {
        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.scholarshipTagId = scholarshipTagId;
        this.summary = summary;
        this.imagePath = imagePath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSummary(String summary){
        this.summary = summary;
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

    public String getSummary(){
        return summary;
    }

    public String getImagePath(){
        return imagePath;
    }
}
