package org.example;

import net.thauvin.erik.crypto.CryptoPrice;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
            } else if (text.equals("btc")) {
                var price = CryptoPrice.spotPrice("BTC");
                message.setText("BTC price: " + price.getAmount().doubleValue());
            } else if (text.equals("etc")) {
                var price = CryptoPrice.spotPrice("ETH");
                message.setText("ETH price: " + price.getAmount().doubleValue());
            }  else if (text.equals("sol")) {
            var price = CryptoPrice.spotPrice("SOL");
            message.setText("SOL price: " + price.getAmount().doubleValue());

            } else if (text.equals("/all")) {
                StringBuilder allCoin = new StringBuilder("Current prices:\n");
                allCoin.append("BTC: ").append(CryptoPrice.spotPrice("BTC").getAmount().doubleValue()).append("\n");
                allCoin.append("ETH: ").append(CryptoPrice.spotPrice("ETH").getAmount().doubleValue()).append("\n");
                allCoin.append("SOL: ").append(CryptoPrice.spotPrice("SOL").getAmount().doubleValue()).append("\n");
                message.setText(allCoin.toString());
            }  else {
                message.setText("Unknown command!");
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
