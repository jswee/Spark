package com.sparkapp.spark.thread;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SocketHandler implements Runnable {

    BluetoothSocket socket;
    List<Character> buf;

    public SocketHandler(BluetoothSocket socket) {
        this.socket = socket;
        buf = new ArrayList<Character>();
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
                    buf.add((char)val);
                    Log.d("BLUETOOTH", buf.toString());
                }
            }
        }).start();

        OutputStream out = null;
        try {
            out = socket.getOutputStream();
            out.write(42);
        } catch(IOException ex) {
            Log.e("BLUETOOTH", "Error writing to output stream", ex);
        }
    }
}
