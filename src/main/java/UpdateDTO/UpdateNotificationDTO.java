package UpdateDTO;

public class UpdateNotificationDTO {
    private int notificationId;
    private boolean read_status;
    private String message;

    public UpdateNotificationDTO() {
    }

    public UpdateNotificationDTO(int notificationId, boolean read_status, String message) {
        this.notificationId = notificationId;
        this.read_status = read_status;
        this.message = message;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
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
