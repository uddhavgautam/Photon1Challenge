package com.upgautam.uddhav.photon1;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.upgautam.uddhav.photon1.fragment.utils.BusStatus;
import com.upgautam.uddhav.photon1.model.IssData;
import com.upgautam.uddhav.photon1.model.iss.IssJson;
import com.upgautam.uddhav.photon1.model.iss.IssResponse;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IssService extends IntentService {

    private OkHttpClient okHttpClient;
    private Gson gson;


    public IssService(String name) {
        super(name);
    }

    public IssService() {
        super("IssService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //runs on separate worker thread. Here I can make network requests
        Bundle bundle = intent.getExtras();
        Location mLocation = (Location) bundle.get("location");

        //get lat and lon from the location
        double latVal = mLocation.getLatitude();
        double lonVal = mLocation.getLongitude();

        String url = "http://api.open-notify.org/iss-pass.json?lat=" + latVal + "&lon=" + lonVal;
        okHttpClient = new OkHttpClient();


        gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        Type listType = new TypeToken<IssJson>() {
        }.getType();
        IssJson issJson;

        try {
            String jsonString = getJson(url);
            issJson = gson.fromJson(jsonString, listType); //deserialization

            if (issJson != null) {
                List<IssResponse> responseList = issJson.getIssResponse();

                for (IssResponse resp : responseList
                        ) {
                    IssData issData = new IssData(resp.getDuration().toString(), resp.getRisetime().toString());
                    //now I have the updated model. Now, use event bus to nofity fragment tha he can update his recyclerview

                    App.bus.post(new BusStatus(123)); //post event into the Otto bus

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getJson(String url) throws IOException {
        String jsonStr = "";
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        if (response.isSuccessful()) {
            jsonStr = response.body().string();
        } else {
            jsonStr = null;
        }
        return jsonStr;

        /*

        IssRequest: http://api.open-notify.org/iss-pass.json?lat=45&lon=34

Pojo Generator: http://www.jsonschema2pojo.org/



        IssResponse:
        {
  "message": "success",
  "request": {
    "altitude": 100,
    "datetime": 1514134478,
    "latitude": 45.0,
    "longitude": 34.0,
    "passes": 5
  },
  "response": [
    {
      "duration": 308,
      "risetime": 1514172492
    },
    {
      "duration": 619,
      "risetime": 1514178072
    },
    {
      "duration": 638,
      "risetime": 1514183847
    },
    {
      "duration": 613,
      "risetime": 1514189679
    },
    {
      "duration": 632,
      "risetime": 1514195492
    }
  ]
}
         */
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}