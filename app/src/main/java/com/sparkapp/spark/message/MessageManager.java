package com.sparkapp.spark.message;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {

    private static volatile List<Message> messages = new ArrayList<Message>();

    public static void addMessage(String message) {
        int split = message.indexOf(' ');
        long time = Long.parseLong(message.substring(0, split));
        String msg = message.substring(split);
        messages.add(new Message(time, msg));
    }
}