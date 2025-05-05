package models;

import java.sql.SQLException;
import java.sql.ResultSet;

public class ScholarshipTags {
    private int tagId;
    private int tagName;

    protected ScholarshipTags(int tagId, int tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }
    public static ScholarshipTags getInstance(ResultSet rs) throws SQLException {
        int tagId = rs.getInt("tagId");
        int tagName = rs.getInt("tagName");

        return new ScholarshipTags(tagId, tagName);
    }
    public int getTagId() {
        return tagId;
    }
    public int getTagName() {
        return tagName;
    }
}
