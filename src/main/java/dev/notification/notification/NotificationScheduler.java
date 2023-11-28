package dev.notification.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


@Component
public class NotificationScheduler {
   @Autowired
    private NotificationRepository notificationRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationScheduler.class);

    // ScheduledExecutorService to periodically check and update notifications
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Scheduled(fixedRate = 3600000) // Check every hour 3600000
    public void scheduleNotifications() {
        LOGGER.info("Scheduler started at {}", new Date());
        checkAndPublishScheduledNotifications();
        checkAndArchiveOldPublishedNotifications();
        LOGGER.info("Scheduler completed at {}", new Date());
    }

    private void checkAndPublishScheduledNotifications() {
        List<NotificationClass> scheduledNotifications = notificationRepository.findByStatus("scheduled");

        for (NotificationClass notification : scheduledNotifications) {
            if (isToday(notification.getDate())) {
                // Update notification status to published
                notification.setStatus("published");
                notificationRepository.save(notification);
            }
        }

        LOGGER.debug("Checked and published scheduled notifications");
    }

    private void checkAndArchiveOldPublishedNotifications() {
        List<NotificationClass> publishedNotifications = notificationRepository.findByStatus("published");

        for (NotificationClass notification : publishedNotifications) {
            if (isOlderThanOneMonth(notification.getDate())) {
                // Update notification status to archived
                notification.setStatus("archived");
                notificationRepository.save(notification);
            }
        }
        LOGGER.debug("Checked and archived old published notifications");
    }

    // Check if the given date string is today
    private boolean isToday(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(new Date());
        return today.equals(date);
    }

    // Check if the given date string is older than one month from the current date
    private boolean isOlderThanOneMonth(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date modifiedDate = dateFormat.parse(date);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1); // Subtract one month
            Date oneMonthAgo = calendar.getTime();

            return modifiedDate.before(oneMonthAgo);
        } catch (ParseException e) {
            // Handle parsing exception if needed
            e.printStackTrace();
            return false;
        }
    }
}
