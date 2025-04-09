package models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Applications {
    private int application_id;
    private int student_id;
    private int scholarship_id;
    private Date application_date;
    private String status;

    private Applications(int application_id, int student_id, int scholarship_id, Date application_date, String status) {
        this.application_id = application_id;
        this.student_id = student_id;
        this.scholarship_id = scholarship_id;
        this.application_date = application_date;
        this.status = status;
    }

    public static Applications getInstance(ResultSet resultSet) throws SQLException {
        int application_id = resultSet.getInt("application_id");
        int student_id = resultSet.getInt("student_id");
        int scholarship_id = resultSet.getInt("scholarship_id");
        Date application_date = resultSet.getDate("application_date");
        String status = resultSet.getString("status");

        return new Applications(application_id, student_id,scholarship_id, application_date,status);
    }

    public int getApplication_id() {
        return application_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public int getScholarship_id() {
        return scholarship_id;
    }

    public Date getApplication_date() {
        return application_date;
    }

    public String getStatus() {
        return status;
    }
}
