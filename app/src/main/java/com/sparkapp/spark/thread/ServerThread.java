package com.sparkapp.spark.thread;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.sparkapp.spark.InputHandler;
import com.sparkapp.spark.MainActivity;

import java.io.IOException;

public class ServerThread implements Runnable {

    private BluetoothAdapter adapter;

    public ServerThread(BluetoothAdapter a) {
        adapter = a;
    }

    @Override
    public void run() {
        BluetoothServerSocket serverSocket = null;
        try {
            serverSocket = adapter.listenUsingRfcommWithServiceRecord(adapter.getName(), MainActivity.uuid);
        } catch(IOException ex) {
            Log.e("ERROR", "IO exception initializing server socket", ex);
        }

        boolean done = false;
        while(true) {
            Log.d("BLUETOOTH", "Waiting for client connection");
            try {
                BluetoothSocket socket = serverSocket.accept();
                Log.d("BLUETOOTH", "Connection! " + socket.getRemoteDevice().getName() + socket.getRemoteDevice().getAddress());
                new Thread(new InputHandler(socket)).start();
            } catch(IOException ex) {
                Log.e("ERROR", "Error accepting socket", ex);
                break;
            }
        }

        try {
            serverSocket.close();
        } catch(IOException ex) {
            Log.e("ERROR", "Error closing server socket", ex);
        }
    }
}
