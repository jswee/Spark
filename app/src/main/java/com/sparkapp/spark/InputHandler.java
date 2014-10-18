package com.sparkapp.spark;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

public class InputHandler implements Runnable {

    InputStream in;
    Queue<Byte> buf;

    public InputHandler(InputStream in) {
        this.in = in;
        buf = new LinkedList<Byte>();
    }

    @Override
    public void run() {
        Log.d("CONNNNNNNNECTED!!!one!", "pls");
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
}
