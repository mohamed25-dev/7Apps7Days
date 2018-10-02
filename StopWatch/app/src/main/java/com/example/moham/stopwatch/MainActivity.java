package com.example.moham.stopwatch;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView startButton;
    private ImageView stopButton;
    private ImageView pauseButton;
    private Chronometer chronometer;

    private boolean isCounting = false;
    private long pauseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        stopButton  = findViewById(R.id.stopButton);
        pauseButton = findViewById(R.id.pauseButton);
        chronometer = findViewById(R.id.chronometer);

        pauseButton.setVisibility(View.GONE);
        stopButton.setVisibility(View.GONE);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();

                pauseButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                startButton.setVisibility(View.GONE);

                isCounting = true;
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.stop();

                pauseButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.GONE);
                startButton.setVisibility(View.VISIBLE);

                isCounting = false;
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCounting) {
                    pauseTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                    chronometer.stop();
                    pauseButton.setImageResource(R.drawable.ic_play_circle_grey600_48dp);
                    isCounting = false;
                } else {
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseTime);
                    chronometer.start();
                    pauseButton.setImageResource(R.drawable.ic_pause_circle_grey600_48dp);
                    isCounting = true;
                }

            }
        });
    }
}
