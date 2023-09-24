package com.example.myapplication;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;



public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    private BluetoothAdapter btAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    public void send_command(View v) throws IOException {
        TextView textout = findViewById(R.id.textView2);
        EditText textV = findViewById(R.id.editTextText);
        byte[] bytesx = textV.getText().toString().getBytes();

        //textout.setText(texCommand);
        textout.setText(liist(bytesx));

    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private StringBuilder liist(byte[] bytesx) throws IOException {
        StringBuilder supplierNames = new StringBuilder();
        if (bytesx.length > 0) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission OK");
                Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    // There are paired devices. Get the name and address of each paired device.
                    for (BluetoothDevice device : pairedDevices) {
                        if (device.getName().contains("MOTOG3")) {
                            ParcelUuid[] devl = device.getUuids();
                            for (ParcelUuid parcelUuid : devl) {
                                Log.i(TAG, "Try use: " + parcelUuid);
                                try {
                                    BluetoothSocket rr = device.createRfcommSocketToServiceRecord(UUID.fromString(String.valueOf(parcelUuid)));
                                    rr.connect();
                                    if (rr.isConnected()) {
                                        supplierNames.append("foi");
                                        rr.getOutputStream().write(bytesx);
                                        Log.i(TAG, "=> " + Arrays.toString(bytesx));
                                        rr.close();
                                        break;
                                    }
                                } catch (Exception e) {
                                    supplierNames.append("Error: ").append(e);
                                    Log.i(TAG, "Error: " + e);
                                }

                            }
                        }
                    }
                }
            } else {
                supplierNames.append("Fail BT Permission!");
            }
        }
        return supplierNames;
    }

}
