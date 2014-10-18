package com.sparkapp.spark.thread;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;

public class SocketHandler implements Runnable {

    BluetoothSocket socket;
    Queue<Byte> buf;

    public SocketHandler(BluetoothSocket socket) {
        this.socket = socket;
        buf = new LinkedList<Byte>();
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
                byte val = 0;
                while(true) {
                    try {
                        val = (byte) in.read();
                    } catch (IOException e) {
                        if (e.getMessage().equals("bt socket closed, read return: -1"))
                            break;
                        else
                            Log.e("BLUETOOTH", "Failed to read: " + e);
                    }
                    buf.offer(val);
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
