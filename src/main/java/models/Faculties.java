package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Faculties {
    private final int facultyId;
    private final int universityId;
    private final String name;

    protected Faculties(int facultyId, int universityId, String name) {
        this.facultyId = facultyId;
        this.universityId = universityId;
        this.name = name;
    }

    public static Faculties getInstance(ResultSet rs) throws SQLException {
        int facultyId = rs.getInt("id");
        int universityId = rs.getInt("university_id");
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
