package com.example.jackchu.enterprisewidewirelesslan;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;




public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scan_wifi = (Button) findViewById(R.id.scan_wifi);

        Button L2_start = (Button) findViewById(R.id.L2_btn);

        Button L3_start_act = (Button) findViewById(R.id.start_btn);
        Button stop_act = (Button) findViewById(R.id.stop_btn);

        scan_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scan_wifi_class = new Intent();
                scan_wifi_class.setClass(MainActivity.this, ScanWifi.class);
                startActivity(scan_wifi_class);
            }
        });

        L2_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent L2 = new Intent();
                L2.setClass(MainActivity.this, L2handoff.class);
                startActivity(L2);
            }
        });

        L3_start_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent L4 = new Intent();
                L4.setClass(MainActivity.this, L4connectivity.class);
                startActivity(L4);
            }
        });



    }
}






