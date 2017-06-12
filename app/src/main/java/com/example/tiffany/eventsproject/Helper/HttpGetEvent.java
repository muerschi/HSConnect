package com.example.tiffany.eventsproject.Helper;

import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.EditText;
import com.example.tiffany.eventsproject.Model.Event;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiffany on 10.06.2017.
 */

public class HttpGetEvent extends AsyncTask <Void, Void, ArrayList<Event>> {

    SessionManager session;
    HttpClient httpClient = null;
    HttpGet httpGet = null;
    HttpResponse response = null;
    String res ="";

    ArrayList<Event> eventList = new ArrayList<Event>();

    @Override
    protected ArrayList<Event> doInBackground(Void... params) {

        try {
            httpClient = new DefaultHttpClient();
            httpGet = new HttpGet("http://141.19.164.162:8080/PMAWebServer/event");
            response = httpClient.execute(httpGet);
            InputStream content = response.getEntity().getContent();

            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null) {
                res += s;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONArray array = new JSONArray(res);

            for (int i=0; i<array.length(); i++) {
                Gson gson = new Gson();
                Event e = gson.fromJson(array.getString(i), Event.class);
                eventList.add(e);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return eventList;

    }

}
