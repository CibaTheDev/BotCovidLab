package lv.team3.botcovidlab.adapter.telegram;

import lv.team3.botcovidlab.utils.HerokuUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.json.JsonObject;
import java.util.List;
import java.util.logging.Logger;


/**
 * Main Telegram bot class with Telegram Long Polling Bot overriden methods.
 * Extends TelegramLongPollingCommandBot
 * @author Vladislavs Kraslavskis
 */
@Component
public  class TelegramBot extends TelegramLongPollingCommandBot {
    private JsonObject settings = HerokuUtils.getTelegramSettings();
    private static final Logger log = Logger.getLogger(String.valueOf(TelegramBot.class));

    public TelegramBot() {}

    @Override
    public String getBotUsername() {
        return settings.getString("name");
    }

    @Override
    public void processNonCommandUpdate(Update update) {}


    @Override
    public String getBotToken() {
        return settings.getString("token");
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        System.out.println("1");
        SendMessage sendMessage = new SendMessage();
        for (Update update: updates){
            if (update!=null){
             sendMessage = UpdatesProcessor.handleUpdate(update);}
            else {
                System.out.println("Update is null");
            }
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
