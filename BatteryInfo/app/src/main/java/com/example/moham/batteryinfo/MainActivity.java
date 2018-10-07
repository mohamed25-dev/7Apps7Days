package com.example.moham.batteryinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView levelTextView;
    private TextView voltageTextView;
    private TextView tempTextView;
    private TextView isChargingTextView;
    private ImageView batteryImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        levelTextView   = findViewById(R.id.batteryLevel);
        voltageTextView = findViewById(R.id.batteryVoltage);
        tempTextView    = findViewById(R.id.batteryTemp);
        isChargingTextView = findViewById(R.id.isCharging);
        batteryImageView   = findViewById(R.id.imageView);

        registerReceiver(this.batteryBroadcastReceiver,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private BroadcastReceiver batteryBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level   = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int voltage = intent.getIntExtra("voltage", 0);
            int temp    = intent.getIntExtra("voltagge", 0);
            int status  = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;

            if (isCharging) {
                isChargingTextView.setText("Charging");
                batteryImageView.setImageResource(R.drawable.charging);
            } else {
                isChargingTextView.setText("DisCharging");
                batteryImageView.setImageResource(R.drawable.charged);
            }
            levelTextView.setText("Battery Status : " + level + "%" );
            voltageTextView.setText("Battery Voltage :" + voltage );
            double dTemp = (double) temp/10;
            tempTextView.setText("Battery Temperature : " + dTemp);
        }
    };
}
