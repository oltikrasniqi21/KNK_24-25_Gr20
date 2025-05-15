package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Feedback {
    private int id;
    private int user_id;
    private String message;
    private Timestamp submitted_at;
    private String response; // optional

    protected Feedback(int id, int user_id, String message, Timestamp submitted_at, String response) {
        this.id = id;
        this.user_id = user_id;
        this.message = message;
        this.submitted_at = submitted_at;
        this.response = response;
    }

    public static Feedback getInstance(ResultSet rs) throws SQLException {
        int feedback_id = rs.getInt("id");
        int user_id = rs.getInt("user_id");
        String message = rs.getString("message");
        Timestamp submitted_at = rs.getTimestamp("submitted_at");
        String response = rs.getString("response");

        return new Feedback(feedback_id, user_id, message, submitted_at, response);
    }

    public int getFeedback_id() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getSubmitted_at() {
        return submitted_at;
    }

    public String getResponse() {
        return response;
    }
}