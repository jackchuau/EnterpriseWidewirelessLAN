package com.example.jackchu.enterprisewidewirelesslan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jackchu on 8/10/2017.
 */

public class L3handoff extends AppCompatActivity {

    TextView show;
    WifiManager wm;
    ConnectivityManager cm;
    String ip = "";
    String temp_ip = "";
    DhcpInfo dhcp;
    long start_time = 0;
    long finish_time = 0;
    String AP_address = "";
    String Server_address = "";
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            dhcp = wm.getDhcpInfo();
            AP_address = Formatter.formatIpAddress(dhcp.gateway);
            Server_address = Formatter.formatIpAddress(dhcp.serverAddress);
            if (ConnectionCheck())
                ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            Toast.makeText(context, "current device ip: "+ip+"\nprevious device ip: "+temp_ip + "\ntime: "+(System.currentTimeMillis())
                    + "\nAP's IP address: "+AP_address + "\nsever address: "+Server_address, Toast.LENGTH_LONG).show();

            if (temp_ip.equals("") && !ip.equals("")) {
                start_time = System.currentTimeMillis();
            }
            else if (!temp_ip.equals("") && ip.equals(temp_ip)) {
                finish_time = System.currentTimeMillis();
            }
            temp_ip = ip;

            if (finish_time != 0 && start_time != 0) {
                Toast.makeText(context, "Start at: "+start_time+"\nfinish at: "+finish_time, Toast.LENGTH_LONG).show();
            }
            /*
            * our start_time should be when temp_ip = "" and ip != ""
            * our finish_time should be when temp_ip != "" and temp_ip != ip
            * */

            show = (TextView) findViewById(R.id.text);
            show.setText("device IP address: "+ip+"\nAP's IP address: "+AP_address + "\nsever address: "+Server_address);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        IntentFilter filter_FOUND = new IntentFilter(cm.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, filter_FOUND);
    }


    public boolean ConnectionCheck(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()){
            return true;
        }
        else
            return false;
    }

}

