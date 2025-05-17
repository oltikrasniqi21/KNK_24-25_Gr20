package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Review {
    private int reviewId;
    private int applicationId;
    private int adminId;
    private String reviewNote;
    private LocalDate reviewDate;

    private Review(int reviewId, int applicationId, int adminId, String reviewNote, LocalDate reviewDate) {
        this.reviewId = reviewId;
        this.applicationId = applicationId;
        this.adminId = adminId;
        this.reviewNote = reviewNote;
        this.reviewDate = reviewDate;
    }

    public static Review getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int appId = resultSet.getInt("application_id");
        int admID = resultSet.getInt("admin_id");
        String notes = resultSet.getString("review_notes");
        LocalDate date = resultSet.getDate("review_date").toLocalDate();

        return new Review(id,appId,admID,notes,date);
    }


    public int getReviewId() {
        return reviewId;
    }
    public int getApplicationId() {
        return applicationId;
    }
    public int getAdminId() {
        return adminId;
    }
    public String getReviewNote() {
        return reviewNote;
    }
    public LocalDate getReviewDate() {
        return reviewDate;
    }
}
