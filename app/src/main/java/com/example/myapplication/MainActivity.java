package com.example.myapplication;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Set;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void send_command(View v) {
        TextView textout = findViewById(R.id.textView2);
        EditText textV = findViewById(R.id.editTextText);
        String texCommand = textV.getText().toString();
        //textout.setText(texCommand);
        textout.setText(liist());

    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private StringBuilder liist() {
        StringBuilder supplierNames = new StringBuilder();
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission OK");
            Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                // There are paired devices. Get the name and address of each paired device.
                for (BluetoothDevice device : pairedDevices) {
                    supplierNames.append("\n");
                    supplierNames.append(device.getName());
                    supplierNames.append(" =>");
                    supplierNames.append(device.getAddress()); // MAC address
                }
            }
        } else {
            supplierNames.append("Fail BT Permission!");
        }
        return supplierNames;
    }
}
