package com.example.fleetmanagement.SensorUtil;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import androidx.annotation.Nullable;
public class SensorService extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor tempSensor = null; // init null
    private static final float TEMP_THRESHOLD = 40; // degree celsius

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_STICKY;
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
