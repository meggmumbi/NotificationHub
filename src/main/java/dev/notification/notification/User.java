package dev.notification.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    private long userId;

    private String name;
    private String contacts;
    private String email;
    private String password;

    public User(long UserId, String name, String contacts, String email, String password) {
        this.userId = UserId;
        this.name = name;
        this.contacts = contacts;
        this.email = email;
        this.password = password;
    }
}
