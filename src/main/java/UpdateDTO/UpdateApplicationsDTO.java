package UpdateDTO;

public class UpdateApplicationsDTO {
    private int id;
    private String status;

    public UpdateApplicationsDTO() {};

    public UpdateApplicationsDTO(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
