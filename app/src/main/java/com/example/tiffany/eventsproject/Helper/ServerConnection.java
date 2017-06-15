package com.example.tiffany.eventsproject.Helper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.example.tiffany.eventsproject.Model.User;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ich on 02.06.2017.
 */

public class ServerConnection {

    HttpPost httppost = null;
    HttpClient httpclient = new DefaultHttpClient();

    public boolean Login(String email, String password, SessionManager session) {
        try {

            httppost = new HttpPost("http://141.19.177.55:8080/PMAWebServer/login");

            List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(2);
            namevaluepairs.add(new BasicNameValuePair("email", email));
            namevaluepairs.add(new BasicNameValuePair("password", password));
            try {
                httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity rp = response.getEntity();
            String origresponseText = EntityUtils.toString(rp);
            Gson gson = new Gson();
            // String responseText = origresponseText.substring(7, origresponseText.length());

            if (origresponseText.isEmpty()) {
                User user =null;
                return false;
            } else {

                User user = gson.fromJson(origresponseText, User.class);
                session.createLoginSession(user);
                return true;

            }

        } catch(StringIndexOutOfBoundsException ex){
            return false;
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
        return false;
    }


}
