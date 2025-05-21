package models;

import java.sql.SQLException;
import java.sql.ResultSet;

public class ScholarshipTags {
    private int tagId;
    private String tagName;

    protected ScholarshipTags(int tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }
    public static ScholarshipTags getInstance(ResultSet rs) throws SQLException {
        int tagId = rs.getInt("id");
        String tagName = rs.getString("tag_name");

        return new ScholarshipTags(tagId, tagName);
    }
    public int getTagId() {
        return tagId;
    }
    public String getTagName() {
        return tagName;
    }
}
