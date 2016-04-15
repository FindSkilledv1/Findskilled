package com.findskilled.findskilled;

import android.net.http.HttpResponseCache;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lenovo on 20-03-2016.
 */
public class ServerReq {

    static String stream;
    public String getLocalityName(String cityName) throws IOException {
     try{
        URL url = new URL("http://www.findskilled.in/findskilledAApp/get_locality.php?city_name="+cityName);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        //check the connection status
        if(urlConnection.getResponseCode() == 200) {
            // if response code = 200 ok
             InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            // Read the BufferedInputStream
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
            stream = sb.toString();

            // End reading...............

            // Disconnect the HttpURLConnection
            urlConnection.disconnect();
        } else {
            // Do something
        }
    }catch (MalformedURLException e){
        e.printStackTrace();
    }catch(IOException e){
        e.printStackTrace();
    }finally {


     }
    // Return the data from specified url
    return stream;


    }
}