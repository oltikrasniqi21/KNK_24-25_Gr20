package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Notification {
    private int notification_id;
    private int student_id;
    private String message;
    private Timestamp created_at;
    private boolean read_status;

    protected Notification(int notification_id, int student_id, String message, Timestamp created_at, boolean read_status) {
        this.notification_id = notification_id;
        this.student_id = student_id;
        this.message = message;
        this.created_at = created_at;
        this.read_status = read_status;
    }

    public static Notification getInstance(ResultSet rs) throws SQLException {
        int notification_id = rs.getInt("notification_id");
        int student_id = rs.getInt("student_id");
        String message = rs.getString("message");
        Timestamp created_at = rs.getTimestamp("created_at");
        boolean read_status = rs.getBoolean("read_status");

        return new Notification(notification_id, student_id, message, created_at, read_status);
    }

    public int getNotification_id() {
        return notification_id;
    }

    public int getStudent_id() {
        return student_id;
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
}
