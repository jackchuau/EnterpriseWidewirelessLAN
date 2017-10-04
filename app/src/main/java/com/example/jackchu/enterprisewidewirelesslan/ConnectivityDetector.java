package com.example.jackchu.enterprisewidewirelesslan;

/**
 * Created by jackchu on 2/10/2017.
 */

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;


public class ConnectivityDetector {

    Context context;
    ConnectivityManager connectivityManager;
    public boolean isConnected(){
        connectivityManager = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo()!=null ) {
            return true;
        }
        else
            return false;
    }
}