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

    public static String channel_id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton button = (ImageButton)(findViewById(R.id.connect));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChannelDialog().show(getFragmentManager(), "Channel");
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
}