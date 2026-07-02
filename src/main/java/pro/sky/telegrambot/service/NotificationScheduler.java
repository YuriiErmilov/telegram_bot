package pro.sky.telegrambot.service;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationScheduler {

    private final NotificationTaskRepository repository;

    private final TelegramBot telegramBot;

    public NotificationScheduler(NotificationTaskRepository repository, TelegramBot telegramBot) {
        this.repository = repository;
        this.telegramBot = telegramBot;
    }

    @Scheduled(cron = "0 * * * * *")
    public void checkNotification() {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

        List<NotificationTask> tasks = repository.findByNotificationTime(now);
        for (NotificationTask task : tasks) {
            if(task.getNotificationTime().withSecond(0).withNano(0).equals(now)){
                telegramBot.execute(
                        new SendMessage(task.getChatId(), " Напоминание " + task.getMessage())
                );
                repository.delete(task);
            }
        }
    }
}
