package com.sparkapp.spark;

import android.bluetooth.BluetoothAdapter;

public class DiscoveryProcess extends Thread {

    public static DiscoveryProcess makeProcess() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null)
            return null;
        return new DiscoveryProcess(bluetoothAdapter);
    }

    public BluetoothAdapter adapter;

    public DiscoveryProcess(BluetoothAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void run() {
        
    }
}
