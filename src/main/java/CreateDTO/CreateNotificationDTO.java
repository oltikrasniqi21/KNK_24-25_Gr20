package CreateDTO;

import java.sql.Timestamp;

public class CreateNotificationDTO {
    private int student_id;
    private String message;
    private Timestamp created_at;
    private boolean read_status;

    public CreateNotificationDTO(int student_id, String message, Timestamp created_at, boolean read_status) {
        this.student_id = student_id;
        this.message = message;
        this.created_at = created_at;
        this.read_status = read_status;
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
