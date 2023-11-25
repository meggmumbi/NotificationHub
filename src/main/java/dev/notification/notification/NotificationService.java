package dev.notification.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository _notificationRepository;
    @Autowired
    private  SequenceService _sequenceService;
    @Autowired
    private MongoOperations mongoOperations;

    public NotificationClass createNotification(NotificationClass notifications) {
        Long notificationId = generateNextId("notification_sequence");
        notifications.setNotificationId(notificationId);
        return _notificationRepository.save(notifications);
    }

    private long generateNextId(String sequenceName) {
        // Get the current sequence value for notification IDs
        long sequenceValue = _sequenceService.getSequenceValue(sequenceName);

        // Increment the sequence value and return it
        _sequenceService.incrementSequenceValue(sequenceName);

        return sequenceValue;
    }

    public List<NotificationClass> getAllNotifications() {
        return _notificationRepository.findAll();
    }

    public Optional<NotificationClass> getNotification(Long notificationId) {
        return _notificationRepository.findByNotificationId(notificationId);
    }

    public NotificationClass updateNotification(Long notificationId, NotificationClass updatedNotifications) {
        // Find the existing pharmacy
        Optional<NotificationClass> existingNotificationOptional = _notificationRepository.findByNotificationId(notificationId);

        if (existingNotificationOptional.isPresent()) {
            NotificationClass existingNotification = existingNotificationOptional.get();

            // Update
            if (updatedNotifications.getTitle() != null && !updatedNotifications.getTitle().isEmpty()) {
                existingNotification.setTitle(updatedNotifications.getTitle());
            }
            if (updatedNotifications.getDescription() != null && !updatedNotifications.getDescription().isEmpty()) {
                existingNotification.setDescription(updatedNotifications.getDescription());
            }
            if (updatedNotifications.getPublisher() != null && !updatedNotifications.getPublisher().isEmpty()) {
                    existingNotification.setPublisher(updatedNotifications.getPublisher());
            }
            if (updatedNotifications.getStatus() != null && !updatedNotifications.getStatus().isEmpty()) {
                existingNotification.setStatus(updatedNotifications.getStatus());
            }
            if (updatedNotifications.getDateModified() != null && !updatedNotifications.getDateModified().isEmpty()) {
                existingNotification.setDateModified(updatedNotifications.getDateModified());
            }



            return _notificationRepository.save(existingNotification);
        }

        return null;
    }

    public boolean deleteNotification(Long notificationId) {
        Optional<NotificationClass> notificationOptional = _notificationRepository.findByNotificationId(notificationId);

        if (notificationOptional.isPresent()) {
            _notificationRepository.deleteByNotificationId(notificationId);
            return true;
        }

        return false;
    }
}
