package UpdateDTO;

public class UpdateFeedbackDTO {
    private int feedbackId;
    private String response;

    public UpdateFeedbackDTO(int feedbackId, String response) {
        this.feedbackId = feedbackId;
        this.response = response;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}