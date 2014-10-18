package com.sparkapp.spark.thread;

import android.bluetooth.BluetoothAdapter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DiscoveryThread implements Runnable {

    BluetoothAdapter adapter;

    public DiscoveryThread(BluetoothAdapter a) {
        adapter = a;
    }

    @Override
    public void run() {
        adapter.startDiscovery();
    }

    public static void start(BluetoothAdapter adapter) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new DiscoveryThread(adapter), 120, 120, TimeUnit.SECONDS);
    }
}
