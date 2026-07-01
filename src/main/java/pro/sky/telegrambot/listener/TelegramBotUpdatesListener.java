package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

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
            if(message.equals("/start")) {
                long chatId = update.message().chat().id();
                telegramBot.execute(
                        new SendMessage(chatId,"Здорова кореш!")
                );
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
