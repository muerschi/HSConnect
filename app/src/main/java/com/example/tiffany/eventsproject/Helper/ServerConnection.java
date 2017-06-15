package com.example.tiffany.eventsproject.Helper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.example.tiffany.eventsproject.Model.Fachschaft;
import com.example.tiffany.eventsproject.Model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ich on 02.06.2017.
 */

public class ServerConnection {

    private static String WEBSERVER_IP= "http://141.19.177.56:8080/PMAWebServer";
    HttpPost httppost = null;
    HttpClient httpclient = null;


    public ServerConnection(){
        HttpParams params = new BasicHttpParams();
        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        params.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, HTTP.DEFAULT_CONTENT_CHARSET);
        params.setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, true);
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30 * 1000);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 30 * 1000);

        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);

        this.httpclient = new DefaultHttpClient(conMgr, params);
    }

    public boolean Login(String email, String password, SessionManager session) throws IOException {
        try {
            httppost = new HttpPost( WEBSERVER_IP + "/login");            List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(2);
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
                User user = null;
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
            throw e;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw e;
        } catch (ParseException e) {
        } catch(Exception e) {
            // ConnectionTimeOutexception
            e.printStackTrace();
            throw e;
        }

        return false;
    }

   public boolean Fachschaften(FachschaftenManager fsManager) {

                    try {
                        httppost = new HttpPost( WEBSERVER_IP + "/fachschaft");

                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity rp = response.getEntity();
                        String origresponseText = EntityUtils.toString(rp);
                        Gson gson = new Gson();

                        if (origresponseText.isEmpty()) {
                            //fsList = null;
                            return false;
                        } else {

                            Type collectionType = new TypeToken<List<Fachschaft>>(){}.getType();
                            List<Fachschaft> fsList = (List<Fachschaft>) new Gson()
                                    .fromJson( origresponseText , collectionType);

                            fsManager.refreshFachschaftsList(fsList);
                            //Fachschaft[] mcArray = gson.fromJson(origresponseText, Fachschaft[].class);
                            //fsList = new ArrayList<>(Arrays.asList(mcArray));

                        }

                    } catch (StringIndexOutOfBoundsException ex) {
                        return false;
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return false;
                    } catch (ClientProtocolException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return false;
                    } catch (ParseException e) {
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return false;
                    }


         //Your code goes here
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
