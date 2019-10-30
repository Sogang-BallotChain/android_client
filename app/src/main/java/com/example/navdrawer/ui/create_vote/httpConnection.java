package com.example.navdrawer.ui.create_vote;

//1014 for http connection

import android.os.AsyncTask;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.io.ByteArrayOutputStream;




public class httpConnection {

    String urlString = "http://www.ballotchain.net/user/signup";
    //String urlString = "http://www.ballotchain.net/testapi/20141508";
    private final String USER_AGENT = "Mozilla/5.0";
    //생성자
    httpConnection() {
        ;
    }


    //just connect to remote server.
    public Boolean ConnectRemoteServer() {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //post 세팅
            String data = "{" + "\"email\":\"pineleaf1215@gmail.com\"," +"\"password\":\"1234\""
                    +"}";




            //각종세팅.
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("User-Agent","Mozilla/4.0");
            urlConnection.setRequestMethod("POST");
            //urlConnection.connect();


            //post 방식
            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(data);
            wr.flush();
            wr.close();
            /*
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();
            */

            // 요청 방식 구하기
            System.out.println("getRequestMethod():" + urlConnection.getRequestMethod());
            // 응답 콘텐츠 유형 구하기
            System.out.println("getContentType():" + urlConnection.getContentType());
            // 응답 코드 구하기
            System.out.println("getResponseCode():"    + urlConnection.getResponseCode());
            // 응답 메시지 구하기
            System.out.println("getResponseMessage():" + urlConnection.getResponseMessage());
            //콘텐트 요청
            //System.out.println("getContent():" + urlConnection.getContent());

            //파일 읽어 온다. GET
            InputStream is = urlConnection.getInputStream();
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String result;
            while((result = br.readLine())!= null){
                sb.append(result + "\n");
            }
            result = sb.toString();
            System.out.println("we got"+result);


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

    //


}
