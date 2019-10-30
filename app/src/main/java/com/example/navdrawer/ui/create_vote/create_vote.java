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

import com.example.navdrawer.R;

//for date,time setting 1014
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import android.os.AsyncTask;
//for httpConnection 1014


public class create_vote extends Fragment {
    //for date,time setting 1014 , start, destination.
    int st_y=0, st_m=0, st_d=0, st_h=0, st_mi=0;
    int dt_y=0, dt_m=0, dt_d=0, dt_h=0, dt_mi=0;

    //CreateVoteModel 클래스는 무슨 역할인거지?
    private CreateVoteModel createVoteModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        createVoteModel =
                ViewModelProviders.of(this).get(CreateVoteModel.class);
        View root = inflater.inflate(R.layout.create_vote, container, false);
        /* default code before i write
        final TextView textView = root.findViewById(R.id.text_gallery);
        createVoteModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */
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

            }
        });


        //for httpConnection 1014
        Button CreateVoteButton = root.findViewById(R.id.CreateVoteButton);
        CreateVoteButton.setOnClickListener(new View.OnClickListener(){
            NetworkTask ConnectCreateVoteModel = new NetworkTask();
            @Override
            public void onClick(View v) {
                ConnectCreateVoteModel.execute();
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


    //thread for http connection, real http conntection is executed on 'httpConnection' class
    public class NetworkTask extends AsyncTask<Void,Void,Boolean>{
        private String url;
        private ContentValues values;

        public NetworkTask(){
            ;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            httpConnection ConnectCreateVoteModel = new httpConnection();
            if(ConnectCreateVoteModel.ConnectRemoteServer()) {
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
}