package CreateDTO;

import java.sql.Date;
import java.sql.Timestamp;

public class CreateNewsDTO {
    private String title;
    private String content;
    private Integer scholarshipId; //Integer pasiqe mundet me qene null
    private int postedBy;
    private Date visibleUntil;

    public CreateNewsDTO(String title, String content, Integer scholarshipId, int postedBy, Date visibleUntil) {
        this.title = title;
        this.content = content;
        this.scholarshipId = scholarshipId;
        this.postedBy = postedBy;
        this.visibleUntil = visibleUntil;
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

    public int getPostedBy() {
        return postedBy;
    }

    public Date getVisibleUntil() {
        return visibleUntil;
    }


}
