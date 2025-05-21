package CreateDTO;

import java.time.LocalDate;

public class CreateScholarshipDTO {
    private String scholarship_name;
    private String provider;
    private int amount;
    private LocalDate deadline_date;
    private double required_gpa;
    private int required_year;
    private String required_faculties;
    private String status;

    public CreateScholarshipDTO(String scholarship_name, String provider, int amount, LocalDate deadline_date, double required_gpa, int required_year, String required_faculties) {
        this.scholarship_name = scholarship_name;
        this.provider = provider;
        this.amount = amount;
        this.deadline_date = deadline_date;
        this.required_gpa = required_gpa;
        this.required_year = required_year;
        this.required_faculties = required_faculties;
        this.status = "active";
    }


    public void setAmount(int amount) {
        this.amount = amount;
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

    public String getRequired_faculties() {
        return required_faculties;
    }

    public String getStatus() {
        return status;
    }
}

