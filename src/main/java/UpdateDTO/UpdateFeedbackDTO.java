package UpdateDTO;

public class UpdateFeedbackDTO {
    private int id;
    private String response;

    public UpdateFeedbackDTO(int id, String response) {
        this.id = id;
        this.response = response;
    }

    public int getFeedbackId() {
        return id;
    }

    public void setFeedbackId(int id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}