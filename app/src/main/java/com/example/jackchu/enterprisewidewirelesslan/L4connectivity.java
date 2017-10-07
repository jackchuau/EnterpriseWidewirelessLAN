package com.example.jackchu.enterprisewidewirelesslan;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.view.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import android.widget.EditText;
/**
 * Created by jackchu on 4/10/2017.
 */


/* check TCP connecction */


public class L4connectivity extends AppCompatActivity {

    private static Socket s;
    private static InputStreamReader isr;
    private static BufferedReader br;
    private static PrintWriter printWriter;
    String mss = "test";
    private static String ip = "10.248.154.174";
    EditText e1;
    //    TextView showIP = (TextView) findViewById(R.id.ip);
//    WifiManager wifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_tcp);
        e1 = (EditText) findViewById(R.id.editText1);

//        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
//        String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
//        showIP.setText("IP address: " + ip);

    }

    public void send_text(View v) {
        mss = e1.getText().toString();
        myTask mt = new myTask();
        mt.execute();

    }



    class myTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                s = new Socket(ip, 1234);
                printWriter = new PrintWriter(s.getOutputStream());
                printWriter.write(mss);
                printWriter.flush();
                printWriter.close();
                s.close();

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}







