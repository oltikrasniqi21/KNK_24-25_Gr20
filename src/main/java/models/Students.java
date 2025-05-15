package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Students extends Users {

    private Double gpa;
    private int year_of_study;
    private String university;
    private String faculty;
    private String major;
    private String priority;
    private String proofDocument;

 private Students(int user_id, String password, String first_name, String last_name, String email, String role,
                     Double gpa, int year_of_study, String university, String faculty, String major, String priority) {
        super(user_id, password,first_name, last_name,email,role);
        this.gpa = gpa;
        this.year_of_study = year_of_study;
        this.university = university;
        this.faculty = faculty;
        this.major = major;
        this.priority = priority;
        this.proofDocument = proofDocument;
    }

    public Double getGpa() {
        return gpa;
    }

    public int getYear_of_study() {
        return year_of_study;
    }

    public String getUniversity() {
        return university;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getMajor() {
        return major;
    }

    public String getPriority() {
        return priority;
    }


    public static Students getInstance(ResultSet resultSet) throws SQLException{
        //These can change depending on what the SQL table rows are named, not necessary but good to do for easier to undestand code
        int user_id = resultSet.getInt("user_id"); //user_id can change to UserId as above
        String password_hash = resultSet.getString("password");
        String first_name = resultSet.getString("first_name");
        String last_name = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        String role = resultSet.getString("role");

        Double gpa  = resultSet.getDouble("gpa");
        int year_of_study = resultSet.getInt("year_of_study");
        String university = resultSet.getString("university");
        String faculty = resultSet.getString("faculty");
        String major = resultSet.getString("major");
        String priority = resultSet.getString("priority");
        String proofDocument = resultSet.getString("proof_document");
        String status = resultSet.getString("status");

return new Students(user_id,password_hash,first_name,last_name,email,role,
        gpa,year_of_study, university, faculty,major, priority);

    }



}
