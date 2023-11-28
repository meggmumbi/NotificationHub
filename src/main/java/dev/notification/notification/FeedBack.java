package dev.notification.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "Feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBack {
    @Transient
    public static final String SEQUENCE_NAME = "feedback_sequence";
    @Id
    private Long feedbackId;
    private String name;
    private String email;
    private String firstTime;
    private String found;
    private String reason;
    private String userFriendly;
    private LocalDate date = LocalDate.now();

}
