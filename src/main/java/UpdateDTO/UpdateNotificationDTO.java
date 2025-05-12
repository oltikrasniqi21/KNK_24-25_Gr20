package UpdateDTO;

public class UpdateNotificationDTO {
    private int notificationId;
    private boolean read_status;

    public UpdateNotificationDTO(int notificationId, boolean read_status) {
        this.notificationId = notificationId;
        this.read_status = read_status;
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
}
