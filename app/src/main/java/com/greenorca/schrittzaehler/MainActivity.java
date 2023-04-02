package com.greenorca.schrittzaehler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textView;
    private EreignisHandler handler;

    private class EreignisHandler extends Handler {
        @Override
        public void handleMessage(Message msg){

            updateSchritte(msg.what);
        }
    }

    private void updateSchritte(int schritte){
        textView.setText(Integer.toString(schritte));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.schritte);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.buttonStop).setOnClickListener(this);
        findViewById(R.id.buttonStart).setOnClickListener(this);
        handler = new EreignisHandler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(getLocalClassName(), "onResume has been called");
        handler = new EreignisHandler();
        SchrittzaehlerService.ereignisHandler = handler;
        updateSchritte(SchrittzaehlerService.schritte);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(getLocalClassName(), "onRestart has been called");
        handler = new EreignisHandler();
        SchrittzaehlerService.ereignisHandler = handler;
        updateSchritte(SchrittzaehlerService.schritte);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                SchrittzaehlerService.schritte=0;
                textView.setText(Integer.toString(0)); break;
            case R.id.buttonStart:
                startService(new Intent(this, SchrittzaehlerService.class));
                SchrittzaehlerService.ereignisHandler = handler;
                Log.d(getLocalClassName(),"Schrittzaähler-Service gestartet");
                break;
            case R.id.buttonStop:
                stopService(new Intent(this, SchrittzaehlerService.class));
                break;
            case R.id.imgViewListSensor:
                startActivity(new Intent(this, SensorListActivity.class));
        }
    }
}
