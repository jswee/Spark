package com.sparkapp.spark.thread;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.sparkapp.spark.InputHandler;

import java.io.IOException;

public class ConnectionThread implements Runnable {

    private BluetoothSocket socket;

    public ConnectionThread(BluetoothSocket s) {
        socket = s;
    }

    @Override
    public void run() {
        Log.d("CONNECTING", socket.getRemoteDevice().getName() + " " + socket.getRemoteDevice().getAddress());
        try {
            socket.connect();
        } catch(IOException ex) {
            Log.e("ERROR", "IO error", ex);
            return;
        }

        new Thread(new InputHandler(socket)).start();
        Log.i("INFO", "SUCCESS!");
    }
}
