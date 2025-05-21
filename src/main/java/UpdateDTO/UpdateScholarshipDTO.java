package UpdateDTO;

import models.Scholarships;

import java.time.LocalDate;

public class UpdateScholarshipDTO {
    private int scholarship_id;
    private int amount;
    private LocalDate deadline_date;
    private double required_gpa;
    private int required_year;
    private String requred_faculties;
    private String status;

    public UpdateScholarshipDTO(int scholarship_id, int amount, LocalDate deadline_date,double required_gpa, int required_year, String requred_faculties) {
        this.scholarship_id = scholarship_id;
        this.amount = amount;
        this.deadline_date = deadline_date;
        this.required_gpa = required_gpa;
        this.required_year = required_year;
        this.requred_faculties = requred_faculties;
        this.status = "active";
    }

    public UpdateScholarshipDTO(Scholarships scholarship) {
        this.scholarship_id = scholarship.getScholarship_id();
        this.amount = scholarship.getAmount();
        this.deadline_date = scholarship.getDeadline_date();
        this.required_gpa = scholarship.getRequired_gpa();
        this.required_year = scholarship.getRequired_year();
        this.requred_faculties = scholarship.getRequired_faculties();
        this.status = "active";
    }


    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getScholarship_id() {
        return scholarship_id;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getDeadline_date() {
        return deadline_date;
    }

    public double getRequired_gpa() {
        return required_gpa;
    }

    public int getRequired_year() {
        return required_year;
    }

    public String getRequred_faculties() {
        return requred_faculties;
    }

    public void setActiveStatus(Boolean status) {
        if (status) {
            this.status = "active";
        }else{
            this.status = "deactive";
        }
    }

    public String getStatus() {
        return status;
    }
}
