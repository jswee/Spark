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
        while(true) {
            try {
                buf.offer((byte) in.read());
            } catch (IOException e) {
                Log.e("BLUETOOTH", "Failed to read: " + e);
            }
            Log.d("BLUETOOTH", in.toString());
        }
    }
}
