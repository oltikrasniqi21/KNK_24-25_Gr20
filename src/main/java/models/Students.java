package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Students extends Users {

    private double gpa;
    private int year_of_study;
//    private String levelOfStudy; at the time of edit, not included on the tabelat.sql file so will comment for now
    private String university;
    private String faculty;
    private String major;
    private int courses_left;
    private String priority;
    private String proofDocument;

    private Students(int user_id, String password, String first_name, String last_name, String email, String role,String status,
                     double gpa, int year_of_study,
//                     String levelOfStudy,
                     String university, String faculty, String major, int courses_left, String priority,String proofDocument) {
        super(user_id, password,first_name, last_name,email,role,status);
        this.gpa = gpa;
        this.year_of_study = year_of_study;
//        this.levelOfStudy = levelOfStudy;
        this.university = university;
        this.faculty = faculty;
        this.major = major;
        this.courses_left = courses_left;
        this.priority = priority;
        this.proofDocument = proofDocument;
    }

    public double getGpa() {
        return gpa;
    }

    public int getYear_of_study() {
        return year_of_study;
    }

//    public String getLevelOfStudy() {
//        return levelOfStudy;
//    }

    public String getUniversity() {
        return university;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getMajor() {
        return major;
    }

    public int getCourses_left() {
        return courses_left;
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

        int gpa  = resultSet.getInt("gpa");
        int year_of_study = resultSet.getInt("year_of_study");
//        String levelOfStudy = resultSet.getString("levelOfStudy");
        String university = resultSet.getString("university");
        String faculty = resultSet.getString("faculty");
        String major = resultSet.getString("major");
        int courses_left = resultSet.getInt("courses_left");
        String priority = resultSet.getString("priority");
        String proofDocument = resultSet.getString("proof_document");
        String status = resultSet.getString("status");

        return new Students(user_id,password_hash,first_name,last_name,email,role,status,
        gpa,year_of_study,
//                levelOfStudy,
                university, faculty,major, courses_left, priority,proofDocument);


    }



}
