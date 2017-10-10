package com.example.jackchu.enterprisewidewirelesslan;


import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.text.format.Formatter;
import android.view.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.io.PrintWriter;
import java.net.URL;
import java.net.UnknownHostException;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jackchu on 4/10/2017.
 */


/*
 *check TCP connecction  and L3 handoff
 * */

public class L4connectivity extends AppCompatActivity {

    Intent mServiceIntent;
    Context context;
    MyTask mt;
    WifiManager wm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edt1 = (EditText) findViewById(R.id.start);
        EditText edt2 = (EditText) findViewById(R.id.stop);

        String start_loc = edt1.getText().toString();
        String stop_loc = edt2.getText().toString();

        Button start_btn = (Button) findViewById(R.id.start_btn);
        Button stop_btn = (Button) findViewById(R.id.stop_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect();
            }
        });
        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnect();
            }
        });
    }


    public void connect() {
        mt = new MyTask();
        mt.execute();
    }

    public void disconnect() {
        mt.cancel(true);
    }

    class MyTask extends AsyncTask<String, String, String> {

        //    Context context;
        @Override
        protected String doInBackground(String... params) {

            String host = "202.58.60.194";
            String pre_ip = "";
            String cur_ip = "";
            boolean connect = true;
            wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            pre_ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            while(connect) {
                try {
                    URL url = new URL("http://cse.unsw.edu.au");
                    HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
                    urlconnection.setRequestMethod("GET");
                    urlconnection.setDoInput(true);
                    urlconnection.setConnectTimeout(1000);
                    urlconnection.connect();
                } catch (Exception e) {
                    connect = false;
                }
            }
            /*
             * time that the TCP connection lost.
             */
            long lost_time = System.currentTimeMillis();

            boolean disconnect = true;
            while(disconnect) {
                try {
                    URL url = new URL("http://cse.unsw.edu.au");
                    HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
                    urlconnection.setRequestMethod("GET");
                    urlconnection.setDoInput(true);
                    urlconnection.setConnectTimeout(1000);
                    urlconnection.connect();
                } catch (Exception e) {
                    disconnect = true;
                    continue;
                }
                disconnect = false;
            }
            long reconnect_time = System.currentTimeMillis();
            cur_ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            return "tcp disconnected and reconnected...." + "\ndisconnected at "+lost_time +
                    "\nreconnected at"+reconnect_time+
                    "\nprevious ip: "+ pre_ip+
                    "\ncurrent ip: " + cur_ip+
                    "\nL3 handoff: "+ (reconnect_time-lost_time)+" Milliseconds";
        }

        protected void onPostExecute(String result) {
            Toast.makeText(L4connectivity.this, result, Toast.LENGTH_SHORT).show();
            TextView show = (TextView) findViewById(R.id.L3_L4_data);
            String prev_data = show.getText().toString();
            show.setText(prev_data+"\n"+result);
        }

    }

}







