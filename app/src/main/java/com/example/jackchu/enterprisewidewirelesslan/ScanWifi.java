package com.example.jackchu.enterprisewidewirelesslan;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * Created by jackchu on 4/10/2017.
 */


public class ScanWifi extends AppCompatActivity {
    WifiManager wifiManager;
    Context context;
    List<ScanResult> list;
    int AP_counts;
    String network_details = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();
        AP_counts = count_AP(list);
        network_details = get_details();
        TextView data = (TextView) findViewById(R.id.scan_wifi_data);
        String pre_data = data.getText().toString();
        data.setText(pre_data+"\ndensity: "+AP_counts+"\n"+network_details);
//        Toast.makeText(getApplicationContext(), Integer.toString(AP_counts), Toast.LENGTH_LONG).show();


    }



    public void start() {
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled() && wifiManager.getWifiState() != wifiManager.WIFI_STATE_ENABLING) {
            wifiManager.setWifiEnabled(true);
        }
        list = wifiManager.getScanResults();
    }


    private Integer count_AP(List<ScanResult> list) {
        int counts = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).SSID.equals("uniwide"))
                counts += 1;
        }
        return counts;
    }


    private String get_details() {
        wifiManager =  (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String frequency = Double.toString(wifiManager.getConnectionInfo().getFrequency() * 0.001); // convert MHz to GHz
        String speed = Integer.toString(wifiManager.getConnectionInfo().getLinkSpeed());  // Mbps
        Toast.makeText(getApplicationContext(), "network details", Toast.LENGTH_LONG).show();
        return "frequency: "+frequency+"\nspeed: "+speed;
    }




}
