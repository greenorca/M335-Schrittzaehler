package com.greenorca.schrittzaehler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SensorListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_list);

        TextView sensorListe = findViewById(R.id.txtSensors);

        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.getSensorList(Sensor.TYPE_ALL).forEach(sensor -> {
            sensorListe.append(sensor.getName()+System.lineSeparator());
        });

    }

    public void finish(View v){
        finish();
    }
}
