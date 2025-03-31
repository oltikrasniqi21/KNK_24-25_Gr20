package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Users {
    private int UserId;
    private String password_hash;
    private String first_name;
    private String last_name;
    private String email;
    private String role;

    protected Users(int UserId,String password_hash, String first_name, String last_name, String email, String role) {
        this.UserId = UserId;
        this.password_hash = password_hash;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.role = role;
    }

    public static Users getInstance(ResultSet resultSet) throws SQLException {
        int user_id = resultSet.getInt("user_id");
        String password_hash = resultSet.getString("password_hash");
        String first_name = resultSet.getString("first_name");
        String last_name = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        String role = resultSet.getString("role");

        return new Users(user_id,password_hash,first_name,last_name,email,role);
    }
//
//    public String getPassword_hash() {
//        return password_hash;
//    }

    public int getUserId() {
        return UserId;
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
}
