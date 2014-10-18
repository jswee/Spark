package com.sparkapp.spark;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sparkapp.spark.message.MessageManager;
import com.sparkapp.spark.thread.ConnectionThread;
import com.sparkapp.spark.thread.PoolTable;
import com.sparkapp.spark.thread.ServerThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ChatActivity extends Activity {

    public static final UUID uuid = UUID.fromString("b042294f-84f1-43ca-b7f5-a84631b084f7");
    private LocalBroadcastManager broadcaster;

    BluetoothAdapter adapter;
    List<BluetoothDevice> devices;

    BroadcastReceiver finishedReciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);

        MessageManager.activity = this;

        ImageButton button = (ImageButton)(findViewById(R.id.send_button));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = (EditText) findViewById(R.id.message_input);
                MessageManager.sendMessage(text.getText().toString());
                text.setText("");
            }
        });

        startExclamationPointNoSeriouslyImReallyExcited();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat, menu);
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

    public void startExclamationPointNoSeriouslyImReallyExcited() {
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

        startConnection();
    }

    public void startConnection() {
        serverConnection();
        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
        for(BluetoothDevice device : pairedDevices) {
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
        PoolTable.server = new ServerThread(adapter);
        new Thread(PoolTable.server).start();
    }

    public void clientConnection(BluetoothSocket socket) {
        new Thread(new ConnectionThread(socket)).start();
    }
}
