package com.sparkapp.spark;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sparkapp.spark.thread.SocketHandler;
import com.sparkapp.spark.thread.ServerThread;
import com.sparkapp.spark.thread.ConnectionThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MainActivity extends Activity {

    public static final UUID uuid = UUID.fromString("b042294f-84f1-43ca-b7f5-a84631b084f7");
    private static final int DISCOVERABLE_ACTIVITY_RESULT = 42;
    public static String channel_id = null;

    BluetoothAdapter adapter;
    List<BluetoothDevice> devices;

    BroadcastReceiver finishedReciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);

        ImageButton button = (ImageButton)(findViewById(R.id.send_button));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = (EditText)findViewById(R.id.message_input);
                // PoolTable.socket.writeMessage(text.getText().toString())
                text.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startDiscovery() {
        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            Log.e("BLUETOOTH", "NO BLUETOOTH?!?!?!");
        }
        Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivity(enableBluetoothIntent);

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        devices = new ArrayList<BluetoothDevice>();

        BroadcastReceiver foundReciever = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction()))
                    devices.add((BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
            }
        };
        IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(foundReciever, foundFilter);

        finishedReciever = new BroadcastReceiver() {
            boolean done = false;
            public void onReceive(Context context, Intent intent) {
                adapter.cancelDiscovery();
                if (!done) {
                    startConnection();
                    done = true;
                    Log.d("DONE", "Done with recieving");
                }
            }
        };
        IntentFilter finishedFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(finishedReciever, finishedFilter);

        adapter.startDiscovery();
    }

    public void startConnection() {
        unregisterReceiver(finishedReciever);
        serverConnection();
        Log.d("DEVICES", devices.toString());
        for(BluetoothDevice device : devices) {
            Log.d("STARTING", device.getName() + " " + device.getAddress());
            try {
                clientConnection(device.createRfcommSocketToServiceRecord(uuid));
            } catch (IOException ex) {
                Log.e("ERROR", "Error creating RFCOMM socket", ex);
            }
        }
    }

    public void serverConnection() {
        Log.d("STARTING", "Starting server!");
        new Thread(new ServerThread(adapter)).start();
    }

    public void clientConnection(BluetoothSocket socket) {
        new Thread(new ConnectionThread(socket)).start();
    }
}