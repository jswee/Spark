package com.sparkapp.spark;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MainActivity extends Activity {

    public static final UUID uuid = UUID.fromString("b042294f-84f1-43ca-b7f5-a84631b084f7");

    private boolean initBlue() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if(adapter == null)
            return false;
        if(!adapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.connect))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new ChannelDialog().show(getFragmentManager(), "Channel");
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startDiscovery() {
        final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            Log.e("BLUETOOTH", "NO BLUETOOTH?!?!?!");
        }
        Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBluetoothIntent, 0);

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


        List<BluetoothSocket> sockets = new ArrayList<BluetoothSocket>();
        BluetoothServerSocket serverSocket = null;
        try {
            serverSocket = adapter.listenUsingRfcommWithServiceRecord(adapter.getName(), uuid);
        } catch (IOException e) {
            Log.e("BLUETOOTH", "ERROR: " + e.toString());
        }

        try {
            while (true) {
                BluetoothSocket socket = serverSocket.accept(10);
                sockets.add(socket);
            }
        } catch (IOException e) {
            Log.e("BLUETOOTH", "ERROR: " + e.toString());
        }

    }
}
