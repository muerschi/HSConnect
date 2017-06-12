package com.example.tiffany.eventsproject.Helper;

import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.EditText;
import com.example.tiffany.eventsproject.Model.Event;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Tiffany on 10.06.2017.
 */

public class HttpPostEvent extends AsyncTask <Void, Integer, String>{

    SessionManager session;
    HttpPost httpPost = null;
    HttpClient httpClient = new DefaultHttpClient();
    private Event ev;

    public HttpPostEvent (Event event) {
        this.ev = event;
    }
    @Override
    protected String doInBackground(Void... params) {
        return sendEvent();
    }

    private String sendEvent() {

        /// create Json of Event Object
        Gson js = new Gson();

        // send Json to PMAWebServer
        try {
            httpPost = new HttpPost("http://141.19.164.162:8080/PMAWebServer/event");
            String postString = js.toJson(ev);
            // data
            httpPost.setEntity(new StringEntity(js.toJson(ev)));
            // so Server knows what to do with it
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            // handles response
            HttpResponse response = httpClient.execute(httpPost);
            // on success:
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "Worked";
    }
}
