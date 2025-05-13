package CreateDTO;

import java.sql.Timestamp;

public class CreateNotificationDTO {
    private int student_id;
    private String message;
    private Timestamp created_at;
    private boolean read_status;
    private boolean is_broadcast;

    public CreateNotificationDTO(int student_id, String message, Timestamp created_at, boolean read_status, boolean is_broadcast) {
        this.student_id = student_id;
        this.message = message;
        this.created_at = created_at;
        this.read_status = read_status;
        this.is_broadcast = is_broadcast;
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

    public boolean is_broadcast() {
        return is_broadcast;
    }
}
