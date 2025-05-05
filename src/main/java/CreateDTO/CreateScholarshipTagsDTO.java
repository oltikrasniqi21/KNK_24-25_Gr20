package CreateDTO;

public class CreateScholarshipTagsDTO {
    private String tagName;

    public CreateScholarshipTagsDTO(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}