package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Users {
    private int user_id;
    private String password;
    private String first_name;
    private String last_name;
    private String email;
    private String role;
    private String status;

    protected Users(int user_id, String password, String first_name, String last_name, String email, String role,String status) {
        this.user_id = user_id;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public static Users getInstance(ResultSet resultSet) throws SQLException {
        int user_id = resultSet.getInt("id");
        String password_hash = resultSet.getString("password");
        String first_name = resultSet.getString("first_name");
        String last_name = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        String role = resultSet.getString("role");
        String status= resultSet.getString("status");

        return new Users(user_id,password_hash,first_name,last_name,email,role,status);
    }

//    public String getPassword() {
//        return password;
//    }

    public int getUser_id() {
        return user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }
}
