package com.ppsoln.bt;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button Btn;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver,filter);
        Btn = (Button)findViewById(R.id.btn_click);
        mTextView = (TextView)findViewById(R.id.tv_Main);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothAdapter mbtAdapter = BluetoothAdapter.getDefaultAdapter();
                if(mbtAdapter.isDiscovering()){
                    mbtAdapter.cancelDiscovery();
                }
                mbtAdapter.startDiscovery();
            }
        });

    }
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e("This",action);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.e("This",action);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    Log.e("This",device.getName() + "\n" + device.getAddress());
                    mTextView.append(device.getName() + "\n" + device.getAddress() +"\n");
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.e("This",action);
            }
        }
    };
}

