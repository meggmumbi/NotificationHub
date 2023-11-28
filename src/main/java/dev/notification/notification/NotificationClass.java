package dev.notification.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Document(collection = "Notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationClass {
    @Transient
    public static final String SEQUENCE_NAME = "notification_sequence";
    @Id
    private Long notificationId;
    private String title;
    private String description;
    private String date;
    private String status;
    private String publisher;
    private String dateModified;


}
