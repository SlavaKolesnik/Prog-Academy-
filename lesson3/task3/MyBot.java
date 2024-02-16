package org.example;

import net.thauvin.erik.crypto.CryptoPrice;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyBot extends TelegramLongPollingBot {
    public MyBot() {
        super("6751846970:AAESXW6MtnSoFL3fB-I2sb76h4lL05YGvg0");
    }
    @Override
    public void onUpdateReceived(Update update) {
        var chatId = update.getMessage().getChatId();
        var text = update.getMessage().getText();
        try {
            var message = new SendMessage();
            message.setChatId(chatId);

            if (text.equals("/start")) {
                message.setText("Hello!");
            } else {

                Pattern pattern = Pattern.compile("(btc|eth|sol)\\s*,\\s*(btc|eth|sol)");
                Matcher matcher = pattern.matcher(text);

                if (matcher.find()) {
                    String crypto1 = matcher.group(1);
                    String crypto2 = matcher.group(2);

                    var price1 = CryptoPrice.spotPrice(crypto1.toUpperCase());
                    var price2 = CryptoPrice.spotPrice(crypto2.toUpperCase());

                    message.setText(crypto1.toUpperCase() + " price: " + price1.getAmount().doubleValue() +
                            "\n" + crypto2.toUpperCase() + " price: " + price2.getAmount().doubleValue());
                } else if (text.equals("/all")) {
                    StringBuilder response = new StringBuilder("Current prices:\n");
                    response.append("BTC: ").append(CryptoPrice.spotPrice("BTC").getAmount().doubleValue()).append("\n");
                    response.append("ETH: ").append(CryptoPrice.spotPrice("ETH").getAmount().doubleValue()).append("\n");
                    response.append("SOL: ").append(CryptoPrice.spotPrice("SOL").getAmount().doubleValue()).append("\n");
                    message.setText(response.toString());
                } else {
                    var price = CryptoPrice.spotPrice(text.toUpperCase());
                    message.setText(text.toUpperCase() + " price: " + price.getAmount().doubleValue());
                }
            }


            execute(message);
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    @Override
    public String getBotUsername() {
        return "mysuperbot431345_bot";
    }
}
