package models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Scholarships {
    private int scholarshipId;
    private String scholarshipName;
    private String provider;
    private int amount;
    private Date deadline;
    private int yearRequirement;
    private String majorRequirement;


    private Scholarships(int scholarshipId, String scholarshipName, String provider, int amount, Date deadline, int yearRequirement, String majorRequirement) {
        this.scholarshipId = scholarshipId;
        this.scholarshipName = scholarshipName;
        this.provider = provider;
        this.amount = amount;
        this.deadline = deadline;
        this.yearRequirement = yearRequirement;
        this.majorRequirement = majorRequirement;
    }

    public static Scholarships getInstance(ResultSet resultSet) throws SQLException {
        int scholarshipId = resultSet.getInt(1);
        String scholarshipName = resultSet.getString(2);
        String provider  = resultSet.getString(3);
        int amount = resultSet.getInt(4);
        Date deadline = resultSet.getDate(5);
        int yearRequirement = resultSet.getInt(6);
        String majorRequirement = resultSet.getString(7);
        return new Scholarships(scholarshipId,scholarshipName,provider,amount,deadline,yearRequirement,majorRequirement);

    }

    public int getScholarshipId() {
        return scholarshipId;
    }

    public String getScholarshipName() {
        return scholarshipName;
    }

    public String getProvider() {
        return provider;
    }

    public int getAmount() {
        return amount;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getMajorRequirement() {
        return majorRequirement;
    }

    public int getYearRequirement() {
        return yearRequirement;
    }
}
