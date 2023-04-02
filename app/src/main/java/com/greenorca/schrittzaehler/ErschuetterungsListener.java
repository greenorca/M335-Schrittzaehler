package com.greenorca.schrittzaehler;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;

public class ErschuetterungsListener implements SensorEventListener {

    private Handler handler;
    private boolean schrittBegonnen;
    private float schwellwert = 1.0f;

    public ErschuetterungsListener(Handler h) {
        handler = h;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Log.d("xyz", "sensor changed");
        float y = event.values[1];
        if(schrittBegonnen) {
            if(y>schwellwert) {
                schrittBegonnen = false;
                handler.sendEmptyMessage(1);
            }
        } else {
            if(y< -schwellwert) {
                schrittBegonnen= true;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
