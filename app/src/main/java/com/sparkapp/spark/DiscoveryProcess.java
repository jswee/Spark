package com.sparkapp.spark;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

public class DiscoveryProcess extends Thread {

    public BluetoothAdapter adapter;

    public DiscoveryProcess(BluetoothAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void run() {

    }
}

/*
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    if (adapter == null) {
       // No bluetooth qq
    }
    Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);
    DiscoveryProcess dp = new DiscoveryProcess(adapter);
*/
