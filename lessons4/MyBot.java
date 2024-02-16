package org.example;

import net.thauvin.erik.crypto.CryptoPrice;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyBot extends TelegramLongPollingBot {
    private final HashMap<String, String> cryptoSymbols = new HashMap<>();
    public MyBot() {
        super("6751846970:AAESXW6MtnSoFL3fB-I2sb76h4lL05YGvg0");
        cryptoSymbols.put("BTC", "Bitcoin.png");
        cryptoSymbols.put("ETH", "Ethrereum.png");
        cryptoSymbols.put("SOL", "Solana.png");
    }
    @Override
    public void onUpdateReceived(Update update) {
        var chatId = update.getMessage().getChatId();
        var text = update.getMessage().getText().toUpperCase();

        try {
            if (text.equals("/start")) {
                sendMessage(chatId, "Hello!");
            } else if (cryptoSymbols.containsKey(text)) {
                String crypto = text;
                String imageFileName = cryptoSymbols.get(crypto);
                sendPicture(chatId, imageFileName);
                sendPrice(chatId, crypto);
            } else if (text.matches("^(BTC|ETH) \\d+$")) {
                processCommand(chatId, text);
            } else {
                sendMessage(chatId, "Unknown cryptocurrency or command format!");
            }
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    void processCommand(long chatId, String text) throws Exception {
        Pattern pattern = Pattern.compile("^(BTC|ETH) (\\d+)$");
        Matcher matcher = pattern.matcher(text.trim());

        if (matcher.find()) {
            String crypto = matcher.group(1);
            double amount = Double.parseDouble(matcher.group(2));

            double price = CryptoPrice.spotPrice(crypto).getAmount().doubleValue();
            double quantity = amount / price;

            sendMessage(chatId, String.format("За $%.2f, ви можете придбати %.8f %s по ціні $%.2f per %s.", amount, quantity, crypto, price, crypto));
        } else {
            sendMessage(chatId, "Unknown command format!");
        }
    }

    void sendPrice(long chatId, String name) throws Exception {
        var price = CryptoPrice.spotPrice(name);
        sendMessage(chatId, name + " price: " + price.getAmount().doubleValue());
    }

    void sendPicture(long chatId, String name) throws Exception {
        var photo = getClass().getClassLoader().getResourceAsStream(name);

        var message = new SendPhoto();
        message.setChatId(chatId);
        message.setPhoto(new InputFile(photo, name));
        execute(message);
    }

    void sendMessage(long chatId, String text) throws Exception {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        execute(message);
    }

    @Override
    public String getBotUsername() {
        return "mysuperbot431345_bot";
    }
}
