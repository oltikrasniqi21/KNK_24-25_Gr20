package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Scholarships {
    private int scholarship_id;
    private String scholarship_name;
    private String provider;
    private int amount;
    private LocalDate deadline_date;
    private double required_gpa;
    private int required_year;
    private String required_faculties;
    private String status;


    private Scholarships(int scholarship_id, String scholarship_name, String provider, int amount, LocalDate deadline_date,double required_gpa, int required_year, String requred_major,String status) {
        this.scholarship_id = scholarship_id;
        this.scholarship_name = scholarship_name;
        this.provider = provider;
        this.amount = amount;
        this.deadline_date = deadline_date;
        this.required_gpa = required_gpa;
        this.required_year = required_year;
        this.required_faculties = requred_major;
        this.status= status;
    }

    public static Scholarships getInstance(ResultSet resultSet) throws SQLException {
        int scholarship_id = resultSet.getInt("id");
        String scholarship_name = resultSet.getString("scholarship_name");
        String provider  = resultSet.getString("provider");
        int amount = resultSet.getInt("amount");
        LocalDate deadline_date = resultSet.getDate("deadline_date").toLocalDate();
        int required_year = resultSet.getInt("required_year");
        double required_gpa = resultSet.getDouble("required_gpa");
        String required_major = resultSet.getString("required_major");
        String status = resultSet.getString("status");
        return new Scholarships(scholarship_id, scholarship_name,provider,amount,deadline_date,required_gpa, required_year,required_major,status);
    }

    public String toString(){
        return this.provider.toUpperCase() + " - " +  this.scholarship_name.toUpperCase();
    }

    public int getScholarship_id() {
        return scholarship_id;
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

    public double getRequired_gpa(){
        return  required_gpa;
    }

    public String getRequired_faculties() {
        return required_faculties;
    }

    public int getRequired_year() {
        return required_year;
    }

    public String getStatus() {
        return status;
    }
}
