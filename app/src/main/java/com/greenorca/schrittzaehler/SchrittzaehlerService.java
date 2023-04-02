package com.greenorca.schrittzaehler;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class SchrittzaehlerService extends Service {

    private SensorManager sensorManager;
    private ErschuetterungsListener erschuetterungsListener;
    SchritteListener schritteListener;
    public static int schritte;

    public static Handler ereignisHandler;

    private static class ErschuetterungsHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            schritte++;
            if (ereignisHandler!=null){
                ereignisHandler.sendEmptyMessage(schritte);
            }
        }
    }

    public SchrittzaehlerService() {
        super();
        ErschuetterungsHandler erschuetterungsHandler = new ErschuetterungsHandler();
        erschuetterungsListener = new ErschuetterungsListener(erschuetterungsHandler);
        //schritteListener = new SchritteListener(erschuetterungsHandler);

    }

    /**
     * kann auch mit onStart gemacht werden. seltsamerweise wird der Service nach einigen Minuten zerstört...
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(erschuetterungsListener, sensor, SensorManager.SENSOR_DELAY_GAME);
        //Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        //sensorManager.registerListener(schritteListener, sensor, SensorManager.SENSOR_DELAY_GAME);
        return START_STICKY;
        //PreferencesCheck.loadPreferences(this);
    }

    @Override
    public void onDestroy(){
        sensorManager.unregisterListener(schritteListener);
        Log.d(getClass().getSimpleName(),"Schrittzähler-Service zerstört (intern)");
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
