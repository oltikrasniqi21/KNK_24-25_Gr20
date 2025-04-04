package models;

import java.util.Date;
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

    public static Applications getInstance(ResultSet resultSet) throws SQLException {
        int application_id = resultSet.getInt("application_id");
        int student_id = resultSet.getInt("student_id");
        int scholarship_id = resultSet.getInt("scholarship_id");
        LocalDate application_date = resultSet.getDate("application_date").toLocalDate();
        String status = resultSet.getString("status");

        return new Applications(application_id,student_id,scholarship_id,application_date,status);
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
