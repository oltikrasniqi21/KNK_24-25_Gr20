package Services;

import CreateDTO.CreateNotificationDTO;
import Repository.NotificationRepository;
import UpdateDTO.UpdateNotificationDTO;
import models.Notification;

import java.util.List;

public class NotificationService {
    private final NotificationRepository notificationRepository = new NotificationRepository();

    public Notification create(CreateNotificationDTO dto) {
        return notificationRepository.create(dto);
    }

    public Notification update(UpdateNotificationDTO dto) {
        return notificationRepository.update(dto);
    }

    public List<Notification> getNotificationsForCurrentStudent() {
        int studentId = CurrentUser.getUserId();
        return notificationRepository.getNotificationsByStudentId(studentId);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.getAll();
    }
}
