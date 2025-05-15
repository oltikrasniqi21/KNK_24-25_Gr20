package models;

import java.util.Date;

public class ApplicationsDetails {
    public String studentName;
    public String email;
    public double gpa;
    public int coursesLeft;
    public String priority;
    public String scholarshipName;
    public double requiredGpa;
    public int requiredYear;
    public Date deadline;
    public String status;
    public int currentYear;
    public Date applicationDate;


    public ApplicationsDetails(String studentName, String email, double gpa, int coursesLeft, String priority, String scholarshipName,
                               double requiredGpa, int requiredYear, Date deadline, String status, int currentYear, Date applicationDate) {
        this.studentName = studentName;
        this.email = email;
        this.gpa = gpa;
        this.coursesLeft = coursesLeft;
        this.priority = priority;
        this.scholarshipName = scholarshipName;
        this.requiredGpa = requiredGpa;
        this.requiredYear = requiredYear;
        this.deadline = deadline;
        this.status = status;
        this.currentYear = currentYear;
        this.applicationDate = applicationDate;
    }
}
