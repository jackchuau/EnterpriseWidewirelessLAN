package com.example.jackchu.enterprisewidewirelesslan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.RouteInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jackchu on 10/10/2017.
 */


public class L2handoff extends AppCompatActivity {

    TextView show;
    WifiManager wm;
    ConnectivityManager cm;
    String ip = "";
    String temp_ip = "";
    DhcpInfo dhcp;
    long start_time = -1;
    long finish_time = -1;
    String AP_address = "";
    String Server_address = "";
    RouteInfo route;

    private APchangeReceiver mReceiver = new APchangeReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        IntentFilter AP_change = new IntentFilter(wm.SUPPLICANT_STATE_CHANGED_ACTION);
        registerReceiver(mReceiver, AP_change);
        CheckIp check = new CheckIp();
        check.execute();
    }


    public boolean ConnectionCheck(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()){
            return true;
        }
        else
            return false;
    }

    class CheckIp extends AsyncTask<String,Boolean, Void> {

        @Override
        protected Void doInBackground(String... strings) {

            while(true) {
                wm = (WifiManager)getSystemService(Context.WIFI_SERVICE);
                int networkID = wm.getConnectionInfo().getNetworkId();
                if(networkID == -1) {
                    Toast.makeText(L2handoff.this, "AP changes.......", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public class APchangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (WifiManager.SUPPLICANT_STATE_CHANGED_ACTION.equals(action)) {
//                Toast.makeText(context, "AP connect.....", Toast.LENGTH_SHORT).show();
                SupplicantState state = intent.getParcelableExtra(WifiManager.EXTRA_NEW_STATE);
                if (state == SupplicantState.DISCONNECTED) {
                    Toast.makeText(context, "AP disconnect... awesome!!!!!!", Toast.LENGTH_SHORT).show();
                }
                else if(state == SupplicantState.ASSOCIATED) {
                    /*
                    * this counts as the start_time, which means disconnect from the prev ap
                    * */
                    start_time = System.currentTimeMillis();
                    Toast.makeText(context, "Associated to a new AP....", Toast.LENGTH_SHORT).show();
                }
                else if(state == SupplicantState.AUTHENTICATING) {
                    Toast.makeText(context, "AUTHENTICATING..........", Toast.LENGTH_SHORT).show();
                }
                else if(state == SupplicantState.ASSOCIATING) {
                    Toast.makeText(context, "ASSOCIATING............", Toast.LENGTH_SHORT).show();
                }
                else if(state == SupplicantState.SCANNING) {
                    Toast.makeText(context, "Scanning for networks", Toast.LENGTH_SHORT).show();
                }
                else if(state == SupplicantState.COMPLETED) {
                    /*
                    this counts as the finish_time, which means connect to a new ap
                     */
                    finish_time = System.currentTimeMillis();
                    Toast.makeText(context, "COMPLETED", Toast.LENGTH_SHORT).show();
                    if(start_time != -1) {
                        TextView data_view = (TextView) findViewById(R.id.L2_data);
                        String data = data_view.getText().toString();
                        data = data + "\nL2 handoff delay:  " + (finish_time - start_time);
                        data_view.setText(data);

                    }

                }
                else {
                    String did = state.toString();
                    Toast.makeText(context, did, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

}