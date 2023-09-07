package com.example.fleetmanagement.SensorUtil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
public class SensorService extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor tempSensor = null; // init null
    private static final float TEMP_THRESHOLD = 40; // degree celsius

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
                != null){
            tempSensor =
                    sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        }else {
            Toast.makeText(this, "No Temperature sensor found",
                    Toast.LENGTH_SHORT).show();
        }

        if (tempSensor != null) {
            sensorManager.registerListener(this, tempSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_STICKY;
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() ==
                Sensor.TYPE_AMBIENT_TEMPERATURE){
            float tempData = sensorEvent.values[0];
            Log.d("Sensor data ", "Ambient temperature is: "+tempData+"C");
        }

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
