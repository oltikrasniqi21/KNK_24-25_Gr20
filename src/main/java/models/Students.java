package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Students extends Users {

    private double gpa;
    private int yearOfStudy;
    private String levelOfStudy;
    private String university;
    private String faculty;
    private String field;
    private int coursesLeft;
    private String priority;


    private Students( int UserId,String password_hash, String first_name, String last_name, String email, String role,
                      double gpa, int yearOfStudy, String levelOfStudy, String university, String faculty, String field, int coursesLeft, String priority) {
        super(UserId,password_hash,first_name, last_name,email,role);
        this.gpa = gpa;
        this.yearOfStudy = yearOfStudy;
        this.levelOfStudy = levelOfStudy;
        this.university = university;
        this.faculty = faculty;
        this.field = field;
        this.coursesLeft = coursesLeft;
        this.priority = priority;
    }

    public double getGpa() {
        return gpa;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public String getLevelOfStudy() {
        return levelOfStudy;
    }

    public String getUniversity() {
        return university;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getField() {
        return field;
    }

    public int getCoursesLeft() {
        return coursesLeft;
    }

    public String getPriority() {
        return priority;
    }


    public static Students getInstance(ResultSet resultSet) throws SQLException{
        //These can change depending on what the SQL table rows are named, not necessary but good to do for easier to undestand code
        int user_id = resultSet.getInt("user_id"); //user_id can change to UserId as above
        String password_hash = resultSet.getString("password_hash");
        String first_name = resultSet.getString("first_name");
        String last_name = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        String role = resultSet.getString("role");

        int gpa  = resultSet.getInt("gpa");
        int yearOfStudy = resultSet.getInt("yearOfStudy");
        String levelOfStudy = resultSet.getString("levelOfStudy");
        String university = resultSet.getString("university");
        String fakulteti = resultSet.getString("fakulteti");
        String drejtimi = resultSet.getString("drejtimi");
        int coursesLeft = resultSet.getInt("coursesLeft");
        String prioriteti = resultSet.getString("prioriteti");

        return new Students(user_id,password_hash,first_name,last_name,email,role,
        gpa,yearOfStudy,levelOfStudy,university,fakulteti,drejtimi,coursesLeft,prioriteti);


    }



}
