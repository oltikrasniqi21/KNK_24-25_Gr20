package UpdateDTO;

public class UpdateReviewDTO {
    private int reviewId;
    private String reviewNotes;

    public UpdateReviewDTO(int reviewId, String reviewNotes) {
        this.reviewId = reviewId;
        this.reviewNotes = reviewNotes;
    }

    public int getReviewId() {
        return reviewId;
    }
    public String getReviewNotes() {
        return reviewNotes;
    }
    public void setReviewNotes(String reviewNotes) {
        this.reviewNotes = reviewNotes;
    }
}
