package com.upgautam.uddhav.photon1;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 12/24/2017.
 */

public class App extends Application { //App class for: 1) providing application context 2)Starting Otto Bus

    public static Context AppContext;
    public static OttoBus bus;

    @Override
    public void onCreate() {
        super.onCreate();

        AppContext = getApplicationContext();
        bus = OttoBus.getOttoBus();
    }
}

