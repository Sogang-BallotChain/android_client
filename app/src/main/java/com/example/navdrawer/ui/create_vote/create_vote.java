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
    //for date,time setting 1014
    int y=0, m=0, d=0, h=0, mi=0;

    //CreateVoteModel 클래스는 무슨 역할인거지?
    private CreateVoteModel createVoteModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        createVoteModel =
                ViewModelProviders.of(this).get(CreateVoteModel.class);
        View root = inflater.inflate(R.layout.create_vote, container, false);
        /*
        final TextView textView = root.findViewById(R.id.text_gallery);
        createVoteModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */
        //for date,time setting 1014
        Button button1 = root.findViewById(R.id.month);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        } );
        Button button2 = root.findViewById(R.id.time);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showTime();
            }
        });
        Button button3 = root.findViewById(R.id.set_due);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(), y+"."+m+"."+d+"\n"+h+":" + mi, Toast.LENGTH_SHORT).show();

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

    void showDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view,int year,int month, int dayOfMonth){
                y = year;
                m = month+1;
                d = dayOfMonth;
            }
        },2019,1,11);
        datePickerDialog.setMessage("메시지");
        datePickerDialog.show();
    }

    void showTime(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                h = hourOfDay;
                mi = minute;
            }
        }, 21, 12, true);

        timePickerDialog.setMessage("메시지");
        timePickerDialog.show();
    }

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