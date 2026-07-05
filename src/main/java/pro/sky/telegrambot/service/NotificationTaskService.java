package pro.sky.telegrambot.service;


import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;


@Service
public class NotificationTaskService {
    private final NotificationTaskRepository repository;

    public NotificationTaskService(NotificationTaskRepository repository) {
        this.repository = repository;
    }

    public void saveNotificationTask (long chatId, String message, LocalDateTime notificationTime) {
        NotificationTask task = new NotificationTask();

        task.setChatId(chatId);
        task.setMessage(message);
        task.setNotificationTime(notificationTime);

        repository.save(task);
    }

}
