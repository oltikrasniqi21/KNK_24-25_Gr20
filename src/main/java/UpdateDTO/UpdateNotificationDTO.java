package UpdateDTO;

public class UpdateNotificationDTO {
    private boolean read_status;

    public UpdateNotificationDTO(boolean read_status) {
        this.read_status = read_status;
    }

    public boolean isRead_status() {
        return read_status;
    }

    public void setRead_status(boolean read_status) {
        this.read_status = read_status;
    }
}
