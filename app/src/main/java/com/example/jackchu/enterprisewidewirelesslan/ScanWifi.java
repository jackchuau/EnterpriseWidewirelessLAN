package com.example.jackchu.enterprisewidewirelesslan;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
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

import static android.R.id.list;

/**
 * Created by jackchu on 4/10/2017.
 */


public class ScanWifi extends AppCompatActivity {
    WifiManager wifiManager;
    Context context;
    List<ScanResult> list;
    int AP_counts = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();
        count_AP(list);
        Toast.makeText(getApplicationContext(), Integer.toString(AP_counts), Toast.LENGTH_LONG).show();


    }

    public void start() {
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        Toast.makeText(getApplicationContext(), "all good 000", Toast.LENGTH_LONG).show();
        if (!wifiManager.isWifiEnabled() && wifiManager.getWifiState() != wifiManager.WIFI_STATE_ENABLING) {
            wifiManager.setWifiEnabled(true);
        }
        list = wifiManager.getScanResults();
        sort(list);
        final ListView listView = (ListView) findViewById(R.id.listView);
        if (list == null) {
            Toast.makeText(context, "wifi closed！", Toast.LENGTH_LONG).show();
        } else {
            listView.setAdapter(new MyAdapter(this, list));
        }
    }


    private void count_AP(List<ScanResult> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).SSID.equals("uniwide"))
                AP_counts += 1;
        }
    }

    private void sort(List<ScanResult> list) {
        for (int i = 0; i < list.size(); i++)
            for (int j = 0; j < list.size(); j++) {
                if (list.get(i).level < list.get(j).level) {
                    ScanResult temp = null;
                    temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
    }

    public class MyAdapter extends BaseAdapter {

        LayoutInflater inflater;
        List<ScanResult> list;

        public MyAdapter(Context context, List<ScanResult> list) {
            // TODO Auto-generated constructor stub
            this.inflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view = null;
            view = inflater.inflate(R.layout.wifiviewlist, null);
            ScanResult scanResult = list.get(position);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(scanResult.SSID);
            TextView signalStrenth = (TextView) view.findViewById(R.id.signal_strenth);
            signalStrenth.setText(String.valueOf(Math.abs(scanResult.level)));

            return view;
        }
    }


}
