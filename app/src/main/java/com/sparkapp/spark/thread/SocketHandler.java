package com.sparkapp.spark.thread;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.sparkapp.spark.message.Message;
import com.sparkapp.spark.message.MessageManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SocketHandler implements Runnable {

    BluetoothSocket socket;
    StringBuilder buf;
    OutputStream out;

    public SocketHandler(BluetoothSocket socket) {
        this.socket = socket;
        buf = new StringBuilder();
    }

    @Override
    public void run() {
        Log.d("CONNNNNNNNECTED!!!one!", "pls");

        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream in = null;
                try {
                    in = socket.getInputStream();
                } catch (IOException e) {
                    Log.e("SOCKET", e.getMessage(), e);
                }
                int val = 0;
                while(true) {
                    try {
                        val = in.read();
                    } catch (IOException e) {
                        if (e.getMessage().equals("bt socket closed, read return: -1"))
                            break;
                        else
                            Log.e("BLUETOOTH", "Failed to read: " + e);
                    }
                    buf.append((char)val);
                    if ((char)val == '\n') {
                        MessageManager.addMessage(buf.toString());
                        buf = new StringBuilder();
                    }
                    Log.d("BLUETOOTH", buf.toString());
                }
            }
        }).start();

        try {
            out = socket.getOutputStream();
        } catch(IOException e) {
            Log.e("BLUETOOTH", "Error writing to output stream", e);
        }
    }

    public void writeMessage(Message m) {
        try {
            out.write(m.toString().getBytes());
        } catch (IOException e) {
            Log.d("WRITIN'", e.getMessage(), e);
        }
    }
}
