package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Students extends Users {

    private Double gpa;
    private int year_of_study;
    private String university;
    private String faculty;
    private String major;



    private Students(int user_id, String password, String first_name, String last_name, String email, String role, String status,
                     Double gpa, int year_of_study, String university, String faculty, String major) {
        super(user_id, password,first_name, last_name,email,role,status);
        this.gpa = gpa;
        this.year_of_study = year_of_study;
        this.university = university;
        this.faculty = faculty;
        this.major = major;



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




    public static Students getInstance(ResultSet resultSet) throws SQLException{

        int user_id = resultSet.getInt("id");
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

        String status = resultSet.getString("status");

return new Students(user_id,password_hash,first_name,last_name,email,role,status,
        gpa,year_of_study, university, faculty,major);

    }



}
