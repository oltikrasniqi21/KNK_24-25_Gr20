package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Notification {
    private int id;
    private Integer student_id;
    private String message;
    private Timestamp created_at;
    private boolean read_status;
    private boolean is_broadcast;



    protected Notification(int id, int student_id, String message, Timestamp created_at, boolean read_status, boolean is_broadcast) {
        this.id = id;
        this.student_id = student_id;
        this.message = message;
        this.created_at = created_at;
        this.read_status = read_status;
        this.is_broadcast = is_broadcast;
    }

    public static Notification getInstance(ResultSet rs) throws SQLException {
        int notification_id = rs.getInt("id");
        int student_id = rs.getInt("student_id");
        String message = rs.getString("message");
        Timestamp created_at = rs.getTimestamp("created_at");
        boolean read_status = rs.getBoolean("read_status");
        boolean is_broadcast = rs.getBoolean("is_broadcast");

        return new Notification(notification_id, student_id, message, created_at, read_status, is_broadcast);
    }

    public int getNotification_id() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public boolean isRead_status() {
        return read_status;
    }

    public boolean is_broadcast() {
        return is_broadcast;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
