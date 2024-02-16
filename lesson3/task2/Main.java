package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception{
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new MyBot());
    }
}

// mysuperbot431345_bot
// 6751846970:AAESXW6MtnSoFL3fB-I2sb76h4lL05YGvg0
