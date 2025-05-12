package CreateDTO;

import java.time.LocalDate;

public class CreateReviewDto {
    private int reviewId;
    private int applicationId;
    private int adminId;
    private String reviewNote;
    private LocalDate reviewDate;

    public CreateReviewDto(int reviewId, int applicationId, int adminId, String reviewNote, LocalDate reviewDate) {
        this.reviewId = reviewId;
        this.applicationId = applicationId;
        this.adminId = adminId;
        this.reviewNote = reviewNote;
        this.reviewDate = reviewDate;
    }


    public int getReviewId() {
        return reviewId;
    }
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
    public int getApplicationId() {
        return applicationId;
    }
    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }
    public int getAdminId() {
        return adminId;
    }
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
    public String getReviewNote() {
        return reviewNote;
    }
    public void setReviewNote(String reviewNote) {
        this.reviewNote = reviewNote;
    }
    public LocalDate getReviewDate() {
        return reviewDate;
    }
    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}
