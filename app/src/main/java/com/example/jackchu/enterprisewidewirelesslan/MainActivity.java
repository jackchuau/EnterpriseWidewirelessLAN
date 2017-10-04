package com.example.jackchu.enterprisewidewirelesslan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button scan_wifi = (Button) findViewById(R.id.button);

        scan_wifi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent scan_wifi_class = new Intent();
                scan_wifi_class.setClass(MainActivity.this, ScanWifi.class);
                startActivity(scan_wifi_class);
            }
        });

        Button save_data = (Button) findViewById(R.id.save);
        save_data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent save_data_tofile = new Intent();
                save_data_tofile.setClass(MainActivity.this, NetworkDetails.class);
                startActivity(save_data_tofile);
            }
        });

        Button check_tcp = (Button) findViewById(R.id.tcp_check);
        check_tcp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent check_tcp_connection = new Intent();
                check_tcp_connection.setClass(MainActivity.this, L4connectivity.class);
                startActivity(check_tcp_connection);

            }
        });



    }
}






