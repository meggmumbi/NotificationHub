package dev.notification.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired
    private NotificationService _notificationService;

    @PostMapping
    public ResponseEntity<NotificationClass> createNotification(@RequestBody NotificationClass notifications) {

        NotificationClass createdNotification = _notificationService.createNotification(notifications);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NotificationClass>> getAllNotifications() {

        List<NotificationClass> notifications = _notificationService.getAllNotifications();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationClass> getNotification(@PathVariable String notificationId) {

        Optional<NotificationClass> notification = _notificationService.getNotification(Long.parseLong(notificationId));
        if (notification.isPresent()) {
            return new ResponseEntity<>(notification.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<NotificationClass> updateNotification(@PathVariable String notificationId, @RequestBody NotificationClass updatedNotification) {

        NotificationClass updatedNotificationEntity = _notificationService.updateNotification(Long.parseLong(notificationId), updatedNotification);

        if (updatedNotificationEntity != null) {
            return new ResponseEntity<>(updatedNotificationEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deletePharmacy(@PathVariable String notificationId) {

        boolean deleted = _notificationService.deleteNotification(Long.parseLong(notificationId));

        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
