package UpdateDTO;

public class UpdateScholarshipTagsDTO {
    private int tagId;
    private String tagName;

    public UpdateScholarshipTagsDTO(int tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public int getTagId() {
        return tagId;
    }

    public String getTagName() {
        return tagName;
    }
}
