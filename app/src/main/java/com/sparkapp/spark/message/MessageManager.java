package com.sparkapp.spark.message;

import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sparkapp.spark.ChatActivity;
import com.sparkapp.spark.R;
import com.sparkapp.spark.thread.PoolTable;
import com.sparkapp.spark.thread.SocketHandler;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {

    private static volatile List<Message> messages = new ArrayList<Message>();
    public static ChatActivity activity;

    public static void addMessage(final String message) {
        messages.add(new Message(message));
        /*activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView list = (ListView) activity.findViewById(R.id.message_list);
                list.setAdapter(new ArrayAdapter<Message>(activity, R.layout.fragment_chat, messages));
            }
        });*/
        Log.d("RECIEVED", message);
    }

    public static void sendMessage(String str) {
        Message msg = new Message(System.currentTimeMillis(), str);
        messages.add(msg);
        for (SocketHandler sh : PoolTable.sockets) {
            sh.writeMessage(msg);
        }
    }
}