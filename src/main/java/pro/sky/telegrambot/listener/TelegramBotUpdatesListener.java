package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.NotificationTaskService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private NotificationTaskService service;


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            if(update.message() == null || update.message().text() == null) {
                return;
            }
            logger.info("Processing update: {}", update);
            String message = update.message().text();
            long chatId = update.message().chat().id();
            if(message.equals("/start")) {
                telegramBot.execute(
                        new SendMessage(chatId,"Здравствуйте, напишите напоминание в формате 01.07.2026 20:00 Купить сахар")
                );
                return;
            }
            String[] parts = message.split(" ",3);
            if(parts.length < 3) {
                telegramBot.execute(new SendMessage(chatId, "Неверный формат сообщения. Пример 01.07.2026 20:00 Купить сахар"));
                return;
            } try {
                String dataTimeString = parts[0] +  " " + parts[1];

                LocalDateTime time = LocalDateTime.parse(
                        dataTimeString, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                String text = parts[2];
                service.saveNotificationTask(chatId, text, time);
                telegramBot.execute(new SendMessage(chatId,"Напоминание принято и записанно: " + time + " -> " +  text));
            } catch (Exception e) {
                telegramBot.execute(new SendMessage(chatId, "Ошибка формата даты. Используйте: 01.07.2026 20:00 Купить сахар"));
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
