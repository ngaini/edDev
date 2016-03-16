package edu.scu.levelup;

import android.app.Application;

import com.batch.android.Batch;
import com.batch.android.Config;

/**
 * Created by Nathan on 3/15/2016.
 */
public class MyApplication extends Application
{
    private final String API_KEY = "DEV56E7BC3BA4A1F6DF11D0CAB6148";
    @Override
    public void onCreate() {
        super.onCreate();
        Batch.Push.setGCMSenderId("175791489113");

        Batch.setConfig(new Config(API_KEY));
    }
}
