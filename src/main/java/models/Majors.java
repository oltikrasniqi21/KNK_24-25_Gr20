package models;

import java.sql.SQLException;
import java.sql.ResultSet;

public class Majors {
    private int majorId;
    private int facultyId;
    private String name;

    protected Majors(int majorId, int facultyId, String name) {
        this.majorId = majorId;
        this.facultyId = facultyId;
        this.name = name;
    }
    public static Majors getInstance(ResultSet rs) throws SQLException {
        int majorId = rs.getInt("majorId");
        int facultyId = rs.getInt("facultyId");
        String name = rs.getString("name");

        return new Majors(majorId, facultyId, name);
    }

    public int getMajorId() {
        return majorId;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getName() {
        return name;
    }
}
