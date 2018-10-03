package com.example.moham.voicerecorder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int RECORD_AUDIO_REQUEST_CODE = 123;

    private Button recordButton;
    private Button playButton;

    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;

    private boolean isRecording;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermissionsToRecordAudio();
        }

        recordButton = findViewById(R.id.recordButton);
        playButton   = findViewById(R.id.playButton);

        isRecording = false;

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playButton.setEnabled(false);
                if (!isRecording) {
                    try {
                        prepareForRecording();
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IOException e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                    isRecording = true;
                    Toast.makeText(MainActivity.this, "Recording", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    try {
                        mediaRecorder.stop();
                        mediaRecorder.release();
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                    isRecording = false;
                    playButton.setEnabled(true);
                    Toast.makeText(MainActivity.this, "Recording Completed ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(fileName);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.getMessage();
                    e.printStackTrace();
                }
                mediaPlayer.start();
                Toast.makeText(MainActivity.this, "Playing Record ",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getPermissionsToRecordAudio() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission
                .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission
                .RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO },
                    RECORD_AUDIO_REQUEST_CODE);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                grantResults[2] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permissions Granted, thanks :)",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "I can do nothing without the permissions :(",
                        Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        }
    }

    private void prepareForRecording() {
        fileName = createFileName();

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(fileName);

    }

    private String createFileName () {
        File root   = android.os.Environment.getExternalStorageDirectory();
        File file = new File(root.getAbsolutePath() + "/VoiceRecorder/Audios");

        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = root.getAbsolutePath() + "/VoiceRecorder/Audios/" +
                String.valueOf(System.currentTimeMillis()+ ".mp3");

        return fileName;
    }
}
