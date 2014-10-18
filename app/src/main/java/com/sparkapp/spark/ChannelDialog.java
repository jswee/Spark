package com.sparkapp.spark;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ChannelDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View layout;

        return new AlertDialog.Builder(getActivity())
                .setView((layout = inflater.inflate(R.layout.dialog_channel, null)))
                .setPositiveButton("Connect!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.channel_id = ((EditText) layout.findViewById(R.id.channel_id))
                                .getText().toString();
                        Log.i("INFO", MainActivity.channel_id);
                        dialogInterface.cancel();

                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .create();
    }
}
