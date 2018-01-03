package com.upgautam.uddhav.photon1;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by Administrator on 12/24/2017.
 */

public class OttoBus extends Bus { //this is Otto Bus

    private static OttoBus ottoBus = new OttoBus();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private OttoBus() {
    }

    public static OttoBus getOttoBus() {
        return ottoBus;
    }

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {

            super.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    OttoBus.super.post(event);
                }
            });
        }
    }
}