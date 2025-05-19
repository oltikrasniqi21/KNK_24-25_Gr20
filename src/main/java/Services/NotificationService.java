package Services;

import Repository.NotificationRepository;
import models.Notification;

import java.util.List;

public class NotificationService {
    private final NotificationRepository notificationRepository = new NotificationRepository();

    public List<Notification> getNotificationsForCurrentStudent() {
        int studentId = CurrentUser.getUserId();
        return notificationRepository.getNotificationsByStudentId(studentId);
    }
}
