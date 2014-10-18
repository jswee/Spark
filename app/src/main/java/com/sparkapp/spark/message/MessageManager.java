package com.sparkapp.spark.message;

import com.sparkapp.spark.thread.PoolTable;
import com.sparkapp.spark.thread.SocketHandler;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {

    private static volatile List<Message> messages = new ArrayList<Message>();

    public static void addMessage(String message) {
        messages.add(new Message(message));
    }

    public static void sendMessage(String str) {
        Message msg = new Message(System.currentTimeMillis(), str);
        messages.add(msg);
        for (SocketHandler sh : PoolTable.sockets) {
            sh.writeMessage(msg);
        }
    }
}