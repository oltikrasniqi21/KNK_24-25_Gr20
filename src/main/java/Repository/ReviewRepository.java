package Repository;

import CreateDTO.CreateReviewDto;
import UpdateDTO.UpdateReviewDTO;
import models.Review;

import java.sql.*;

public class ReviewRepository extends BaseRepository<Review, CreateReviewDto, UpdateReviewDTO>{
    public ReviewRepository(){
        super("review");
    }


    @Override
    public Review fromResultSet(ResultSet res) throws SQLException {
        return Review.getInstance(res);
    }

    @Override
    public Review create(CreateReviewDto reviewDto) {
        String query = """
                INSERT INTO REVIEW(APPLICATION_ID, ADMIN_ID, REVIEW_NOTES, REVIEW_DATE)
                VALUES(?,?,?,?)
                """;
        try{
            PreparedStatement statement = this.connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, reviewDto.getApplicationId());
            statement.setInt(2, reviewDto.getAdminId());
            statement.setString(3, reviewDto.getReviewNote());
            statement.setDate(4, Date.valueOf(reviewDto.getReviewDate()));
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()){
                int id = resultSet.getInt(1);
                return this.getById(id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    Review update(UpdateReviewDTO updateDto) {
        String query = "UPDATE REVIEW SET REVIEW_NOTES = ? WHERE ID = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, updateDto.getReviewNotes());
            statement.setInt(2, updateDto.getReviewId());

            int updateRecords = statement.executeUpdate();
            if(updateRecords == 1){
                return this.getById(updateDto.getReviewId());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
