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
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION);
//            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
//
//        }else{
//            list = wifiManager.getScanResults();
//            //do something, permission was previously granted; or legacy device
//        }

//        String size = Integer.toString(list.size());
//        Toast.makeText(getApplicationContext(), "all good 000" + "size is " + size, Toast.LENGTH_LONG).show();

//        sort(list);
//        final ListView listView = (ListView) findViewById(R.id.listView);
//        if (list == null) {
//            Toast.makeText(context, "wifi closed！", Toast.LENGTH_LONG).show();
//        } else {
//            listView.setAdapter(new MyAdapter(this, list));
//        }
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
