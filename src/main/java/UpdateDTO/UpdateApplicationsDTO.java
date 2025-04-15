package UpdateDTO;

public class UpdateApplicationsDTO {
    private String status;

    public UpdateApplicationsDTO() {};

    public UpdateApplicationsDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
