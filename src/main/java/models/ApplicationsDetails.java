package models;

import java.util.Date;

public class ApplicationsDetails {
    public String studentName;
    public String email;
    public double gpa;
    public String priority;
    public String scholarshipName;
    public double requiredGpa;
    public int requiredYear;
    public Date deadline;
    public String status;
    public int currentYear;
    public Date applicationDate;
    public String transcriptPath;


    public ApplicationsDetails(String studentName, String email, double gpa, String priority, String scholarshipName,
                               double requiredGpa, int requiredYear, Date deadline, String status, int currentYear, Date applicationDate, String transcriptPath) {
        this.studentName = studentName;
        this.email = email;
        this.gpa = gpa;
        this.priority = priority;
        this.scholarshipName = scholarshipName;
        this.requiredGpa = requiredGpa;
        this.requiredYear = requiredYear;
        this.deadline = deadline;
        this.status = status;
        this.currentYear = currentYear;
        this.applicationDate = applicationDate;
        this.transcriptPath = transcriptPath;
    }
}
