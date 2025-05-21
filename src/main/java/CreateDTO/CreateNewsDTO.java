package CreateDTO;

import java.sql.Date;
import java.sql.Timestamp;

public class CreateNewsDTO {
    private String title;
    private Integer scholarshipTagId; //Integer pasiqe mundet me qene null
    private int postedBy;
    private String summary;
    private String imagePath;


    public CreateNewsDTO(String title, Integer scholarshipTagId, int postedBy, String summary, String imagePath) {
        this.title = title;
        this.scholarshipTagId = scholarshipTagId;
        this.postedBy = postedBy;
        this.summary = summary;
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public Integer getScholarshipTagId() {
        return scholarshipTagId;
    }

    public int getPostedBy() {
        return postedBy;
    }

    public String getSummary(){
        return summary;
    }

    public String getImagePath(){
        return imagePath;
    }

}
