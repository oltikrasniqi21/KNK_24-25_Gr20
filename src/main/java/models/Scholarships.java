package models;

import java.time.LocalDate;

public class Scholarships {
    private int scholarshipId;
    private String scholarshipName;
    private String provider;
    private int amount;
    private LocalDate deadlineDate;
    private int yearRequirement;
    private String majorRequirement;


    public Scholarships(int scholarshipId, String scholarshipName, String provider, int amount, LocalDate deadlineDate, int yearRequirement, String majorRequirement) {
        this.scholarshipId = scholarshipId;
        this.scholarshipName = scholarshipName;
        this.provider = provider;
        this.amount = amount;
        this.deadlineDate = deadlineDate;
        this.yearRequirement = yearRequirement;
        this.majorRequirement = majorRequirement;
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

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public String getMajorRequirement() {
        return majorRequirement;
    }

    public int getYearRequirement() {
        return yearRequirement;
    }
}
