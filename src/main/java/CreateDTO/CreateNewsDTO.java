package CreateDTO;

import java.sql.Date;
import java.sql.Timestamp;

public class CreateNewsDTO {
    private String title;
    private String content;
    private Integer scholarshipTagId; //Integer pasiqe mundet me qene null
    private int postedBy;

    public CreateNewsDTO(String title, String content, Integer scholarshipTagId, int postedBy) {
        this.title = title;
        this.content = content;
        this.scholarshipTagId = scholarshipTagId;
        this.postedBy = postedBy;
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

    public int getPostedBy() {
        return postedBy;
    }




}
