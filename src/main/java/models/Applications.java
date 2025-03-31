package models;

import java.time.LocalDate;


public class Applications {
    private int applicationId;
    private int studentId;
    private int scholarshipId;
    private LocalDate applicationDate;
    private String status;

    public Applications(int applicationId, int studentId, int scholarshipId, LocalDate applicationDate, String status) {
        this.applicationId = applicationId;
        this.studentId = studentId;
        this.scholarshipId = scholarshipId;
        this.applicationDate = applicationDate;
        this.status = status;
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
