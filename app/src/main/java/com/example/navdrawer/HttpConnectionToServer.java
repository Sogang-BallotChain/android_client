package com.example.navdrawer;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class  HttpConnectionToServer {

    String urlString = "http://www.ballotchain.net/user/signup";

    //생성자 no meaning
    public HttpConnectionToServer() {
        ;
    }

    //connect to server and request and get vote imformation

    //connect to server and POST vote creation

    //connect to server and create Account, 1101
    public Boolean CreateAccount(String email, String password) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //post 세팅
            String data = "{" + "\"email\":\""+email+"\"," +"\"password\":\""+password+"\"" +"}";


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
            urlConnection.disconnect();
            // [yh] 중복 유저시 예외처리.
            if (result.equals("{\"success\": 0, \"message\": \"Duplicate user\"}\n")) {
                return false;
            }
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
    //connect to server and create Account, 1101
    public Boolean LogIn(String email, String password) {
        try {
            urlString = "http://www.ballotchain.net/user/signin";
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //post 세팅
            String data = "{" + "\"email\":\""+email+"\"," +"\"password\":\""+password+"\"" +"}";


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
            urlConnection.disconnect();
            // [yh] 백엔드에서 로그인이 처리가 안될 시 예외처리.
            if (!result.equals("{\"success\": 1}\n")) {
                return false;
            }
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

    //connect to server and create Account, 1113
    public Boolean CreateVote(JSONObject JO) {
        try {
            urlString = "http://www.ballotchain.net/vote/register/";
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //각종세팅.
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("User-Agent","Mozilla/4.0");
            urlConnection.setRequestMethod("POST");
            //urlConnection.connect();


            System.out.println("we sent "+JO.toString());

            //post 방식
            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            System.out.println("clients sending :"+JO.toString() +"\n");
            wr.write(JO.toString().getBytes("utf-8"));
            wr.flush();
            wr.close();

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
            System.out.println("we got "+result);
            urlConnection.disconnect();
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

    // 투표 결과 조회 api과 연결.
    public String GetVoteInfomation(Integer voteId) {
        try {
            urlString = "http://www.ballotchain.net/vote/";
            urlString = urlString + Integer.toString(voteId);   // URL 뒤에 투표코드를 concat.

            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //각종세팅.
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("User-Agent","Mozilla/4.0");
            urlConnection.setRequestMethod("GET");


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

            /*
            //post 방식
            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            System.out.println("clients sending :"+JO.toString() +"\n");
            wr.write(JO.toString().getBytes("utf-8"));
            wr.flush();
            wr.close();
            */

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
            // [yh] 해당 투표코드가 존재하지 않는 경우.
            if (result.equals("{\"success\": 0, \"message\": \"No such vote\"}\n")) {
                System.out.println("empty!!!!!!!!");
                return "empty";
            }

            urlConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            System.out.println("malformed url exception\n");
            e.printStackTrace();
            return "false";
        }catch(IOException e){
            System.out.println("IOException\n");
            e.printStackTrace();
            return "false";
        }
    }

    public Boolean JoinVote(JSONObject JO) {
        try {
            urlString = "http://www.ballotchain.net/vote/";
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //각종세팅.
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("User-Agent","Mozilla/4.0");
            urlConnection.setRequestMethod("POST");
            //urlConnection.connect();


            System.out.println("we sent "+JO.toString());

            //post 방식
            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            System.out.println("clients sending :"+JO.toString() +"\n");
            wr.write(JO.toString().getBytes("utf-8"));
            wr.flush();
            wr.close();

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
            System.out.println("we got "+result);
            urlConnection.disconnect();
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
