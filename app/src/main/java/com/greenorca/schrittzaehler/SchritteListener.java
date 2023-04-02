package com.greenorca.schrittzaehler;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.util.Log;

public class SchritteListener implements SensorEventListener {

    private Handler handler;
    private boolean schrittBegonnen;
    private float schwellwert = 1.0f;

    public SchritteListener(Handler h) {
        handler = h;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("Schritt gezählt", "sensor ewert:" + event.values[0]);
        handler.sendEmptyMessage((int)event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
