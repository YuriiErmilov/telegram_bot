package pro.sky.telegrambot.model;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table (name = "notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "message")
    private String message;

    @Column(name = "notification_time")
    private LocalDateTime notificationTime;

    public NotificationTask() {

    }

    public Long getId() {
        return id;
    }

    public Long getChatId() {
        return chatId;
    }
    public String getMessage() {
        return message;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }
    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }


}
