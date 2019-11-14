package com.example.navdrawer.ui.create_vote;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.navdrawer.HttpConnectionToServer;
import com.example.navdrawer.R;

//for date,time setting 1014
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//for HttpConnectionToServer 1101

public class create_vote extends Fragment {




    JSONObject createVoteJson(String voteName, long st_unix, long dt_unix, JSONArray candidates,String email){
        JSONObject createVoteRequest = new JSONObject();

        try{
            createVoteRequest.put("name",voteName);
            createVoteRequest.put("start_time", st_unix);
            createVoteRequest.put("end_time", dt_unix);
            createVoteRequest.put("candidate_list",candidates);
            createVoteRequest.put("email",email);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return createVoteRequest;
    }

    //vote name
    EditText editTextName;
    JSONArray candidate_list = new JSONArray();

    //for date,time setting 1014 , start, destination.
    String email;
    int st_y=0, st_m=0, st_d=0, st_h=0, st_mi=0;
    int dt_y=0, dt_m=0, dt_d=0, dt_h=0, dt_mi=0;
    long st_unix= 1000,dt_unix = 10000;

    //CreateVoteModel 클래스는 무슨 역할인거지?
    private CreateVoteModel createVoteModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        createVoteModel =
                ViewModelProviders.of(this).get(CreateVoteModel.class);
        View root = inflater.inflate(R.layout.create_vote, container, false);
        //get text from TextField
        editTextName = root.findViewById(R.id.vote_topic);



        //for date,time setting 1014 ,setting starting date
        Button button_date_st = root.findViewById(R.id.st_month);
        button_date_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                st_showDate();
            }
        } );
        Button button_time_st = root.findViewById(R.id.st_time);
        button_time_st.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                st_showTime();
            }
        });
        Button st_button_set_datetime = root.findViewById(R.id.st_set_due);
        st_button_set_datetime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(), st_y+"."+st_m+"."+st_d+"\n"+st_h+":" + st_mi, Toast.LENGTH_SHORT).show();
                st_unix = dateToUnixTime(st_y,st_m,st_d,st_h,st_mi);
            }
        });

        //1027 we should set end_date to.
        Button button_date_dt = root.findViewById(R.id.dt_month);
        button_date_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dt_showDate();
            }
        } );
        Button button_time_dt = root.findViewById(R.id.dt_time);
        button_time_dt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dt_showTime();
            }
        });
        Button dt_button_set_datetime = root.findViewById(R.id.dt_set_due);
        dt_button_set_datetime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(), dt_y+"."+dt_m+"."+dt_d+"\n"+dt_h+":" + dt_mi, Toast.LENGTH_SHORT).show();
                dt_unix = dateToUnixTime(dt_y,dt_m,dt_d,dt_h,dt_mi);
            }
        });


        //for HttpConnectionToServer 1101
        Button CreateVoteButton = root.findViewById(R.id.CreateVoteButton);
        CreateVoteButton.setOnClickListener(new View.OnClickListener(){
            String voteName = editTextName.getText().toString();



            NetworkTask ConnectCreateVoteModel = new NetworkTask();
            @Override
            public void onClick(View v) {
                //for test not implemented list adding ui
                email = "pineleaf1216@gmail.com";
                candidate_list.put("아이유");
                candidate_list.put("사나");
                candidate_list.put("쯔위");
                System.out.println("candidate_list "+candidate_list);
                //System.out.println("st_unix: "+st_unix+"dt_unix"+dt_unix+"\n");

                JSONObject JO  = createVoteJson(voteName,st_unix,dt_unix,candidate_list,email);

                System.out.println("json생성:" + JO.toString() +"\n");
                ConnectCreateVoteModel.execute(JO);
            }

        });


        return root;
    }//onCreate

    //methods for setting starting date,time
    void st_showDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view,int year,int month, int dayOfMonth){
                st_y = year;
                st_m = month+1;
                st_d = dayOfMonth;
            }
        },2019,1,11);
        datePickerDialog.setMessage("메시지");
        datePickerDialog.show();
    }

    void st_showTime(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                st_h = hourOfDay;
                st_mi = minute;
            }
        }, 21, 12, true);

        timePickerDialog.setMessage("메시지");
        timePickerDialog.show();
    }
    //methods for setting destination date,time
    void dt_showDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view,int year,int month, int dayOfMonth){
                dt_y = year;
                dt_m = month+1;
                dt_d = dayOfMonth;
            }
        },2019,1,11);
        datePickerDialog.setMessage("메시지");
        datePickerDialog.show();
    }

    void dt_showTime(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                dt_h = hourOfDay;
                dt_mi = minute;
            }
        }, 21, 12, true);

        timePickerDialog.setMessage("메시지");
        timePickerDialog.show();
    }


    //thread for http connection, real http conntection is executed on 'HttpConnectionToServer' class
    public class NetworkTask extends AsyncTask<JSONObject,Void,Boolean>{
        public NetworkTask(){
            ;
        }

        @Override
        protected Boolean doInBackground(JSONObject... params) {
            HttpConnectionToServer ConnectCreateVoteModel = new HttpConnectionToServer();

            if(ConnectCreateVoteModel.CreateVote(params[0]) ){
                System.out.println("server connected true\n");
                //Toast.makeText(getActivity(), "connected", Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
                System.out.println("server connected false\n");
                //Toast.makeText(getActivity(), "connect failure", Toast.LENGTH_SHORT).show();
                return false;
            }


        }

    }

    //for date,time setting 1014 , start, destination.
    //int st_y=0, st_m=0, st_d=0, st_h=0, st_mi=0;

    long dateToUnixTime(int y,int m,int d,int h,int mi){
        //2019.2.11 21:11
        String dateString = "";
        Date date;

        //concatenate string
        dateString = dateString+ Integer.toString(y) + fill2String(m) + fill2String(d)
                + fill2String(h) + fill2String(mi);

        System.out.println(dateString);

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        try {
            date = dateFormat.parse(dateString);
            long unixTime = (long)date.getTime()/1000;
            System.out.println(unixTime); //<- prints 1352504418
            return unixTime;
        }
        catch (ParseException ex) {
            ex.printStackTrace();
            return -1;
        }


    }

    String fill2String(int x){
        String filled = String.format("%02d",x);
        return filled;
    }

}//create_vote fragment
