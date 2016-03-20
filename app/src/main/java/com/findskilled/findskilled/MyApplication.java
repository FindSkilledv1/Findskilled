package com.findskilled.findskilled;

import android.app.Application;
import android.content.Context;

/**
 * Created by lenovo on 08-03-2016.
 */
public class MyApplication extends Application{
private static MyApplication sInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }
    public static MyApplication getInstance()
    {
        return sInstance;
    }
    //method will return the application context
    public static Context getAppContext()
    {
        return sInstance.getApplicationContext();
    }
}
