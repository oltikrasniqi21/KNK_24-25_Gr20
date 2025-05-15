package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class Applications {
    private int applicationId;
    private int studentId;
    private int scholarshipId;
    private LocalDate applicationDate;
    private String status;

    private Applications(int applicationId, int studentId, int scholarshipId, LocalDate applicationDate, String status) {
        this.applicationId = applicationId;
        this.studentId = studentId;
        this.scholarshipId = scholarshipId;
        this.applicationDate = applicationDate;
        this.status = status;
    }

    public static Applications getInstance(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        int sid = resultSet.getInt("student_id");
        int scid = resultSet.getInt("scholarship_id");
        LocalDate appDate = resultSet.getDate("application_date").toLocalDate();
        String stat = resultSet.getString("status");

        return new Applications(id, sid, scid, appDate, stat);
    }

    public int getApplicationId() {
        return applicationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getScholarshipId() {
        return scholarshipId;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public String getStatus() {
        return status;
    }
}
