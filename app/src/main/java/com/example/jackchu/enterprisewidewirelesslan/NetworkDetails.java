package com.example.jackchu.enterprisewidewirelesslan;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jackchu on 4/10/2017.
 */


public class NetworkDetails extends AppCompatActivity {
    WifiManager mwifiManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mwifiManager =  (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        String frequency = Double.toString(mwifiManager.getConnectionInfo().getFrequency() * 0.001); // convert MHz to GHz
        String speed = Integer.toString(mwifiManager.getConnectionInfo().getLinkSpeed());  // Mbps
        Toast.makeText(getApplicationContext(), "network details", Toast.LENGTH_LONG).show();
        /**
         * use switch to check which protocol is using based on the data rate and frequency detected
         * save data to a file
         */


    }
}