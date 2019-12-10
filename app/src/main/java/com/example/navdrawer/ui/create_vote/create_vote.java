package com.example.navdrawer.ui.create_vote;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.navdrawer.HttpConnectionToServer;
import com.example.navdrawer.R;
import com.example.navdrawer.AddCandidates;

//for date,time setting 1014
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
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
//for HttpConnectionToServer 1101

public class create_vote extends Fragment {

    private RadioGroup radioGroup;




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
    final int REQUEST_TEST = 1;

    //CreateVoteModel 클래스는 무슨 역할인거지?
    private CreateVoteModel createVoteModel;

    EditText edittext;
    Button buttonAdd;
    ArrayList<String> items;
    ArrayAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        createVoteModel =
                ViewModelProviders.of(this).get(CreateVoteModel.class);
        final View root = inflater.inflate(R.layout.create_vote, container, false);
        //get text from TextField
        final EditText emailAddress = (EditText)root.findViewById(R.id.email_addr);
        editTextName = (EditText)root.findViewById(R.id.vote_title);

        //register candidates
        //push Button and move to SeeDiscriptionAndJOinVote
        /*get rid of this part for radio updates

        Button intentButton = root.findViewById(R.id.email_uploads);
        intentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity() , AddCandidates.class);
                startActivityForResult(intent,REQUEST_TEST);
                //startActivity(intent);
            }
        } );
        */


        //화면 ui들
        edittext = (EditText)root.findViewById(R.id.edit_candidates2);
        buttonAdd = (Button)root.findViewById(R.id.AddCandidateButton2);
        Button buttonDelete = (Button)root.findViewById(R.id.DeleteButtonCandidates2);
        Button buttonRegister = (Button)root.findViewById(R.id.RegisterButtonCandidates2);
        //빈 데이터 리스트 생성.
        items = new ArrayList<String>();
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_multiple_choice, items);

        final ListView listview = (ListView) root.findViewById(R.id.listview2);
        listview.setAdapter(adapter);

        //버튼 클릭시 리스트에 추가.
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Editable Text1 = edittext.getText();
                if(Text1.toString() != "") {
                    items.add(Text1.toString());
                    adapter.notifyDataSetChanged();
                    edittext.setText("");
                }
            }
        });


        buttonDelete.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                int count = adapter.getCount() ;

                for (int i = count-1; i >= 0; i--) {
                    if (checkedItems.get(i)) {
                        items.remove(i) ;
                    }
                }

                // 모든 선택 상태 초기화.
                listview.clearChoices() ;

                adapter.notifyDataSetChanged();
            }

        }) ;

        buttonRegister.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v){
                for(int i=0;i<items.size();i++) {
                    System.out.println("#"+i+items.get(i));
                    //    JSONArray candidate_list = new JSONArray();
                    candidate_list.put(items.get(i));
                }
            }

        });


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
            String voteName;

            NetworkTask ConnectCreateVoteModel = new NetworkTask();
            @Override
            public void onClick(View v) {
                //for test not implemented list adding ui
                voteName =  editTextName.getText().toString();
                email = emailAddress.getText().toString();
                System.out.println("candidate_list "+candidate_list);
                //System.out.println("st_unix: "+st_unix+"dt_unix"+dt_unix+"\n");

                JSONObject JO  = createVoteJson(voteName,st_unix,dt_unix,candidate_list,email);

                System.out.println("json생성:" + JO.toString() +"\n");
                ConnectCreateVoteModel.execute(JO);
            }

        });


        return root;
    }//onCreate

/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TEST) {
            final ArrayList<String> items = data.getStringArrayListExtra("items");
            //add to JsonArray here
            for(int i=0;i<items.size();i++) {
                System.out.println("#"+i+items.get(i));
                //    JSONArray candidate_list = new JSONArray();
                candidate_list.put(items.get(i));
            }
        }
    }
*/
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
