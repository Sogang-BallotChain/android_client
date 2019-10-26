package com.example.navdrawer.ui.create_vote;

//1014 for http connection

import android.os.AsyncTask;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class httpConnection {
    String urlString = "http://www.naver.com/";


    httpConnection() {
        ;
    }

    public Boolean ConnectRemoteServer() {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            return true;
        } catch (MalformedURLException e) {
            System.out.println("malformed url exception\n");
            e.printStackTrace();
            return false;
        }catch(IOException e){
            System.out.println("IOException\n");
            e.printStackTrace();
            return false;
        }

    }

}
