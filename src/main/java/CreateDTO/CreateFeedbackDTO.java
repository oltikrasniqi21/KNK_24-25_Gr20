package CreateDTO;

import java.sql.Timestamp;

public class CreateFeedbackDTO {
    private int user_id;
    private String message;
    private Timestamp submitted_at;
    private String response; // optional

    public CreateFeedbackDTO(int user_id, String message, Timestamp submitted_at, String response) {
        this.user_id = user_id;
        this.message = message;
        this.submitted_at = submitted_at;
        this.response = response;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getSubmitted_at() {
        return submitted_at;
    }

    public String getResponse() {
        return response;
    }
}
