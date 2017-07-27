package RimeBotPack;

import RimeBotPack.Rime.Rime;
import RimeBotPack.Rime.DumnRime;
import RimeBotPack.Rime.NormalRime;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.*;

/**
 * Created by anton.a.baranov on 06.07.2017.
 *  This is the Telegram bot, using for finding rimes for the entered worlds
 *
 */
public class SimpleRussianRimeBot extends TelegramLongPollingBot {
    private final RimeBot bot = new RimeBot();

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new SimpleRussianRimeBot());
            System.out.println("Бот зарегистрирован");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "SimpleRussianRimeBot";
    }

    @Override
    public String getBotToken() {
        return "369122388:AAGVl0DT5i4mpBlRSKWkTKioQttD9CGujnw";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            String incomeMessage = message.getText();
            try {
               sendMsg(message, bot.reply(incomeMessage));
            }
            catch (Exception exc) {
               System.out.println(exc);
            }
        }
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
