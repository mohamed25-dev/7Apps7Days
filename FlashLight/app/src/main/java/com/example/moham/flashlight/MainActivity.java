package com.example.moham.flashlight;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
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

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_FLASH_PERMISSION_CODE = 1;
    private Button toggleButton;
    private boolean isEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = findViewById(R.id.button);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getFlashPermission();
        }

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (!isEnabled) {
                  flashLightOn();
                  toggleButton.setText(getResources().getText(R.string.flash_off));
              } else {
                  flashLightOff();
                  toggleButton.setText(getResources().getText(R.string.flash_on));
              }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getFlashPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission
                .CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.CAMERA},
                    REQUEST_FLASH_PERMISSION_CODE);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_FLASH_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permissions Granted, thanks :)",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "I can do nothing without the permissions :(",
                        Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        }
    }

    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, true);
                isEnabled = true;
            }
        } catch (CameraAccessException e) {
            e.getMessage();
            isEnabled = false;
        }

    }

    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, false);
                isEnabled = false;
            }
        } catch (CameraAccessException e) {
            e.getMessage();
            isEnabled = true;
        }
    }

}
