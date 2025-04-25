package Repository;

import CreateDTO.CreateNotificationDTO;
import UpdateDTO.UpdateNotificationDTO;
import models.Notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationRepository extends BaseRepository<Notification, CreateNotificationDTO, UpdateNotificationDTO> {

    public NotificationRepository() {
        super("notification");
    }

    @Override
    Notification fromResultSet(ResultSet rs) throws SQLException {
        return Notification.getInstance(rs);
    }

    @Override
    public Notification create(CreateNotificationDTO dto) {
        String query = "INSERT INTO notification (student_id, message, created_at, read_status) VALUES (?, ?, ?, ?) RETURNING *";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, dto.getStudent_id());
            stmt.setString(2, dto.getMessage());
            stmt.setTimestamp(3, dto.getCreated_at());
            stmt.setBoolean(4, dto.isRead_status());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Notification update(UpdateNotificationDTO dto) {
        String query = "UPDATE notification SET read_status = ? WHERE notification_id = ? RETURNING *";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setBoolean(1, dto.isRead_status());
            stmt.setInt(2, dto.getNotificationId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
