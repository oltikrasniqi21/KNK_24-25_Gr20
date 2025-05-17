package CreateDTO;

import java.time.LocalDate;

public class CreateScholarshipDTO {
    private String scholarship_name;
    private String provider;
    private int amount;
    private LocalDate deadline_date;
    private double required_gpa;
    private int required_year;
    private String required_major;
    private String status;

    public CreateScholarshipDTO(String scholarship_name, String provider, int amount, LocalDate deadline_date, double required_gpa, int required_year, String requred_major) {
        this.scholarship_name = scholarship_name;
        this.provider = provider;
        this.amount = amount;
        this.deadline_date = deadline_date;
        this.required_gpa = required_gpa;
        this.required_year = required_year;
        this.required_major = requred_major;
        this.status = "active";
    }

    public void setScholarship_name(String scholarship_name) {
        this.scholarship_name = scholarship_name;
    }

    public void setProvider(String provider) {
        this.provider = provider;
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

    public void setRequired_major(String required_major) {
        this.required_major = required_major;
    }

    public String getScholarship_name() {
        return scholarship_name;
    }

    public String getProvider() {
        return provider;
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

    public String getRequired_major() {
        return required_major;
    }

    public String getStatus() {
        return status;
    }
}

