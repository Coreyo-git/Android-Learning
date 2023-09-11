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
import java.util.Arrays;

public class SensorService extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor tempSensor = null; // init tempSensor as null
    private static final float TEMP_THRESHOLD = 40; // degree celsius

    private Sensor acceleroSensor = null; // init tempSensor as null
    private static final double ACCEL_THRESHOLD = 9.81; // magnitune threshold 9.8 is not moving?

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);

        // Check if a temperature sensor is available
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
                != null){
            tempSensor =
                    sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        }else {
            Toast.makeText(this, "No Temperature sensor found",
                    Toast.LENGTH_SHORT).show();
        }

        // Check if an accelerometer sensor is available
        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            acceleroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        } else {
            Toast.makeText(this, "No Accelerometer sensor found",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Called when the service is started
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (tempSensor != null) {
            // Register temperature sensor listener
            sensorManager.registerListener(this, tempSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(acceleroSensor != null) {
            // Register accelerometer sensor listener
            sensorManager.registerListener(this, acceleroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        return START_STICKY;
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() ==
                Sensor.TYPE_AMBIENT_TEMPERATURE){
            float tempData = sensorEvent.values[0];
            Log.d("Sensor data ", "Ambient temperature is: "+tempData+"C");
        }

        if (sensorEvent.sensor.getType() ==
                Sensor.TYPE_ACCELEROMETER){
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            double magnitude = Math.sqrt(x * x + y * y + z * z);

            // Check if the acceleration magnitude exceeds the threshold
            if (magnitude > ACCEL_THRESHOLD) {
                Log.d("Sensor data", "Acceleration towards X, Y, and Z " +
                        Arrays.toString(sensorEvent.values) + " and magnitude: " + magnitude);
            }

            // Create an AccelerometerData object and broadcast it to other components
            AccelerometerData accelerometerData = new
                    AccelerometerData(sensorEvent.timestamp, x, y, z, magnitude);
            Intent broadcastIntent = new Intent("VEHICLE_SENSOR_DATA");
            broadcastIntent.putExtra("accelerometerData", accelerometerData);
            sendBroadcast(broadcastIntent);
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
