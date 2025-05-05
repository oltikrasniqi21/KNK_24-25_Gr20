package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Faculties {
    private int facultyId;
    private int universityId;
    private String name;

    protected Faculties(int facultyId, int universityId, String name) {
        this.facultyId = facultyId;
        this.universityId = universityId;
        this.name = name;
    }

    public static Faculties getInstance(ResultSet rs) throws SQLException {
        int faculty_id = rs.getInt("facultyId");
        int university_id = rs.getInt("universityId");
        String name = rs.getString("name");

        return new Faculties(facultyId, universityId, name);
    }

    public int getFacultyId() {
        return facultyId;
    }

    public int getUniversityId() {
        return universityId;
    }

    public String getName() {
        return name;
    }
}
