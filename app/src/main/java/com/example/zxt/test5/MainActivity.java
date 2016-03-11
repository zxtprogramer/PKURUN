package com.example.zxt.test5;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public LocationManager lm;
    public TextView t01,tV;
    public EditText editV;
    public Button bt01,bt02,bt03;
    public MyService.MyBinder binder;

    public ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder=(MyService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t01=(TextView)findViewById(R.id.textView);
        tV=(TextView)findViewById(R.id.TextV);
        editV=(EditText)findViewById(R.id.InputVelocity);
        bt01=(Button)findViewById(R.id.btn01);
        bt02=(Button)findViewById(R.id.bt02);
        bt03=(Button)findViewById(R.id.bt03);

        /*
        lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //Location location=lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location location=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        */
        final Intent intent1=new Intent(MainActivity.this, MyService.class);
        intent1.setAction("android.intent.action.MYS1");
        bindService(intent1, conn, Service.BIND_AUTO_CREATE);

        bt01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startService(intent1);
                double vv=Double.parseDouble(editV.getText().toString());
                binder.setPara(0, vv);
                binder.startRun();
                bt01.setEnabled(false);
                bt02.setEnabled(false);

            }
        });

        bt02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double vv=Double.parseDouble(editV.getText().toString());
                binder.setPara(1, vv);
                binder.startRun();
                bt01.setEnabled(false);
                bt02.setEnabled(false);


            }
        });

        bt03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stopService(intent1);
                binder.stopRun();
                //unbindService(conn);

                bt01.setEnabled(true);
                bt02.setEnabled(true);


            }
        });



       // t01.setText(location.getLatitude()+"");


    }
}
