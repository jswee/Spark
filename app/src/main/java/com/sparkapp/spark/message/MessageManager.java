package com.sparkapp.spark.message;

import java.util.ArrayList;
import java.util.List;

public class MessageManager implements Runnable {
    public volatile List<Message> messages;

    public MessageManager() {
        messages = new ArrayList<Message>();
    }

    @Override
    public void run() {

    }
}