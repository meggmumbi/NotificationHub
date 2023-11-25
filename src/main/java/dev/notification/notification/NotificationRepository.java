package dev.notification.notification;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends MongoRepository<NotificationClass, String> {
    Optional<NotificationClass> findByNotificationId(Long notificationId);
    void deleteByNotificationId(Long notificationId);


}
