package com.findskilled.findskilled;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by lenovo on 14-03-2016.
 */
public class InternetConnectionDetector {

    private Context _context;

    public InternetConnectionDetector(Context context){
        this._context = context;
    }

    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetworkinfo = connectivity.getActiveNetworkInfo();
            if (activeNetworkinfo != null) {
                if(activeNetworkinfo.getType()==ConnectivityManager.TYPE_WIFI)
                {
//                    Toast.makeText(_context, activeNetworkinfo.getTypeName(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                else if(activeNetworkinfo.getType()==ConnectivityManager.TYPE_MOBILE)
                {

//                    Toast.makeText(_context, activeNetworkinfo.getTypeName(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                else
                {
                    return false;
                }


            }
            return false;
    }
}
