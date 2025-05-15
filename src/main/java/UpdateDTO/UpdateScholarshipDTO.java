package UpdateDTO;

import java.sql.Date;
import java.time.LocalDate;

public class UpdateScholarshipDTO {
    private int scholarship_id;
    private int amount;
    private LocalDate deadline_date;
    private double required_gpa;
    private int required_year;
    private String requred_major;

    public UpdateScholarshipDTO(int scholarship_id, int amount, LocalDate deadline_date,double required_gpa, int required_year, String requred_major) {
        this.scholarship_id = scholarship_id;
        this.amount = amount;
        this.deadline_date = deadline_date;
        this.required_gpa = required_gpa;
        this.required_year = required_year;
        this.requred_major = requred_major;
    }

    public void setScholarship_id(int scholarship_id) {
        this.scholarship_id = scholarship_id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDeadline_date(LocalDate deadline_date) {
        this.deadline_date = deadline_date;
    }

    public void setRequired_gpa(double required_gpa) {
        this.required_gpa = required_gpa;
    }

    public void setRequired_year(int required_year) {
        this.required_year = required_year;
    }

    public void setRequred_major(String requred_major) {
        this.requred_major = requred_major;
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

    public String getRequred_major() {
        return requred_major;
    }
}
