package com.upgautam.uddhav.photon1.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.squareup.otto.Subscribe;
import com.upgautam.uddhav.photon1.App;
import com.upgautam.uddhav.photon1.IssService;
import com.upgautam.uddhav.photon1.R;
import com.upgautam.uddhav.photon1.adapter.MyRecyclerViewAdapter;
import com.upgautam.uddhav.photon1.fragment.utils.BusStatus;
import com.upgautam.uddhav.photon1.model.IssData;
import com.upgautam.uddhav.photon1.model.LocationPOJO;

import java.util.List;

public class HomeFragment extends Fragment implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "HomeFragment";
    public static MyRecyclerViewAdapter mMyRecyclerViewAdapter;
    public static Context mContext;
    public static RecyclerView mRecyclerView;
    public static GoogleApiClient mGoogleApiClient;
    public static boolean isLocationUpdated = false;
    public int count = 0;
    public LocationRequest mLocationRequest;
    public LocationSettingsRequest.Builder builderLocationSettings;
    public Location location; // location
    public LocationSettingsRequest mLocationSettingsRequest;
    protected RecyclerView.LayoutManager mLayoutManager;
    LocationPOJO locationPOJO;
    private List<IssData> mIssList;
    private boolean buildFlag = false;
    private Handler handler;


    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        rootView.setTag(TAG);

        mContext = getActivity().getApplicationContext();

        mRecyclerView = rootView.findViewById(R.id.recyclerview_home_fragment);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mMyRecyclerViewAdapter = new MyRecyclerViewAdapter(mIssList, mContext);
        mRecyclerView.setAdapter(mMyRecyclerViewAdapter);

        initEverything();

        return rootView;

    }

    private void initEverything() {
        locationPOJO = new LocationPOJO();
        handler = new Handler(Looper.getMainLooper());

        buildGoogleApiClient();


        if (mGoogleApiClient != null) {
            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            } else {
                if (isLocationUpdated) {
                    if (locationPOJO.getmLocation() != null) {

                        //pass each user location to the service
                        Intent serviceStartingIntent = new Intent();
                        serviceStartingIntent.putExtra("location", locationPOJO.getmLocation());
                        getActivity().getApplicationContext().startService(new Intent(getActivity().getApplicationContext(), IssService.class));

                    }
                }
            }
        } else {
            Log.i(TAG, "GoogleApiClient couldn't build!");
        }
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        buildFlag = true;
    }

    //main thread
    @Override
    public void onLocationChanged(Location location) {
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation != null) {
            isLocationUpdated = true;

            //pass each user location to the service
            Intent serviceStartingIntent = new Intent();
            serviceStartingIntent.putExtra("location", mLocation);
            getActivity().getApplicationContext().startService(new Intent(getActivity().getApplicationContext(), IssService.class));

        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    //main thread
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //This is Async request, obviously should be from the main thread. Because we are not sure,
        //after how many minutes it will get connected. Other threads are possible to die but not main thread
        count++;

        buildLocationSettingsRequest();

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mGoogleApiClient != null) {
                    if (!mGoogleApiClient.isConnected()) {
                        mGoogleApiClient.connect();

                    } else {
                        createLocationRequest();
                    }
                }
            }
        });
    }

    public void createLocationRequest() { //in main thread
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); //10 seconds
        mLocationRequest.setSmallestDisplacement(500); //500 meters changed
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        PendingResult<Status> pendingStatus = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        pendingStatus.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

                if (status.isSuccess()) {

                    if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    Location mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                    if (mLocation != null) {
                        isLocationUpdated = true;

                        //pass each user location to the service
                        Intent serviceStartingIntent = new Intent();
                        serviceStartingIntent.putExtra("location", mLocation);
                        getActivity().getApplicationContext().startService(new Intent(getActivity().getApplicationContext(), IssService.class));

                    }
                }
            }
        });
    }

    private void buildLocationSettingsRequest() {
        builderLocationSettings = new LocationSettingsRequest.Builder(); //null builder
        builderLocationSettings.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builderLocationSettings.build();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "GoogleApiClient suspended!");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "GoogleApiClient failed!");

    }


    //update RecyclerView from the OttoEventBus
    @Subscribe
    public void messageReceived(BusStatus event) {

        if (event.getStatus() == 123 /* success status */) {
            //update the recyclerview
            mMyRecyclerViewAdapter.insert(mMyRecyclerViewAdapter.getItemCount() - 1, new IssData());
        } else /* failed status */ {

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        App.bus.register(getActivity().getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        App.bus.unregister(getActivity().getApplicationContext()); //Unregister of Otto Bus
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
