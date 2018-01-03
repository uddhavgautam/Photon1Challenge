package com.upgautam.uddhav.photon1.model;

import android.location.Location;

/**
 * Created by Administrator on 12/23/2017.
 */

public class LocationPOJO {
    private Location mLocation;

    public LocationPOJO() {
    }

    public LocationPOJO(Location mLocation) {
        this.mLocation = mLocation;
    }

    public Location getmLocation() {
        return mLocation;
    }

    public void setmLocation(Location mLocation) {
        this.mLocation = mLocation;
    }
}
