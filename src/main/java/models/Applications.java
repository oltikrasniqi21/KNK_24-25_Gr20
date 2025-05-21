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
    private String transcriptPath;
    private double gpa;

    private Applications(int applicationId, int studentId, int scholarshipId, LocalDate applicationDate,
                         String status, String transcriptPath, double gpa) {
        this.applicationId = applicationId;
        this.studentId = studentId;
        this.scholarshipId = scholarshipId;
        this.applicationDate = applicationDate;
        this.status = status;
        this.transcriptPath = transcriptPath;
        this.gpa = gpa;
    }

    public static Applications getInstance(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        int sid = resultSet.getInt("student_id");
        int scid = resultSet.getInt("scholarship_id");
        LocalDate appDate = (resultSet.getDate("application_date").toLocalDate());
        String stat = resultSet.getString("status");
        String transcript = resultSet.getString("transcript_path");
        double gpa = resultSet.getDouble("gpa");

        return new Applications(id, sid, scid, appDate, stat, transcript, gpa);
    }

    public int getApplicationId() {
        return applicationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}
