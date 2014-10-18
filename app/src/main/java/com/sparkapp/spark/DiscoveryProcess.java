package com.sparkapp.spark;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DiscoveryProcess extends Thread {

    public BluetoothAdapter adapter;

    public DiscoveryProcess() {

    }

    @Override
    public void run() {
        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            // No bluetooth qq
        }
        Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);

        final List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();

        BroadcastReceiver foundReciever = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction()))
                    devices.add((BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
            }
        };
        IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(foundReciever, foundFilter);

        BroadcastReceiver finishedReciever = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                adapter.cancelDiscovery();
            }
        };
        IntentFilter finishedFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(finishedReciever, finishedFilter);

        adapter.startDiscovery();

        BluetoothServerSocket serverSocket = null;
        try {
            serverSocket = adapter.listenUsingRfcommWithServiceRecord(adapter.getName(),
                    UUID.fromString("b042294f-84f1-43ca-b7f5-a84631b084f7"));
        } catch (IOException e) {
            Log.e("BLUETOOTH_ERROR", e.toString());
        }
    }
}
