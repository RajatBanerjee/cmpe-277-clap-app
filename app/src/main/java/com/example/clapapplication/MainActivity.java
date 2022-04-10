package com.example.clapapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorMgr;
    private Sensor sensor;
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mPlayer =  MediaPlayer.create(this, R.raw.clap);
    }

    protected void onResume() {
        super.onResume();
        sensorMgr.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);

    }


    protected void onPause() {
        super.onPause();
        sensorMgr.unregisterListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float proximityVal = event.values[0];
        if (proximityVal < 5 && !mPlayer.isPlaying()) {
            mPlayer.start();
        } else if (mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //no implementation
    }


}