package com.example.moham.sibha;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;

    private SensorManager sensorManager;

    private float currentAccleratorVal;
    private float lastAccelaratorVal;
    private float shakeVal;

    private Vibrator vibrator;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button   = findViewById(R.id.button);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener, sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        currentAccleratorVal = SensorManager.GRAVITY_EARTH;
        lastAccelaratorVal   = SensorManager.GRAVITY_EARTH;
        shakeVal             = 0.00f;

        textView.setText(String.valueOf(counter));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                if (counter > 0) {
                    button.setText("سبح");
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect
                            .createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(150);
                }
                textView.setText(String.valueOf(counter));

            }
        });

    }

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            //Toast.makeText(MainActivity.this, "Shaked", Toast.LENGTH_SHORT).show();

            lastAccelaratorVal   = currentAccleratorVal;
            currentAccleratorVal = (float) Math.sqrt((double) (x*x + y*y + z*z));

            float delta = currentAccleratorVal - lastAccelaratorVal;
            shakeVal = shakeVal * 0.9f + delta;

            if (shakeVal > 12) {
                counter = 0; //reset the counter;
                textView.setText(String.valueOf(counter));

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
