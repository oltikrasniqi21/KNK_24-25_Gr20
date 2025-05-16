package UpdateDTO;

public class UpdateNotificationDTO {
    private int id;
    private boolean read_status;
    private String message;

    public UpdateNotificationDTO() {
    }

    public UpdateNotificationDTO(int id, boolean read_status, String message) {
        this.id = id;
        this.read_status = read_status;
        this.message = message;
    }

    public int getNotificationId() {
        return id;
    }

    public void setNotificationId(int notificationId) {
        this.id = notificationId;
    }

    public boolean isRead_status() {
        return read_status;
    }

    public void setRead_status(boolean read_status) {
        this.read_status = read_status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
