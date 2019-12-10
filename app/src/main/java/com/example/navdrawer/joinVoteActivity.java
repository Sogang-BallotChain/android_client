package com.example.navdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.JsonReader;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class joinVoteActivity extends AppCompatActivity {

    ArrayList<String> candidates;
    ArrayAdapter adapter;
    TextView VoteTitle;
    Button VoteButton;
    int voteId_Int;
    String name;


    @Override
    public void onBackPressed() {
        //버튼 클릭시 리스트에 추가.
        Intent intent = new Intent();
    //    intent.putExtra("items",items);
        setResult(RESULT_OK,intent);
        finish();

        // super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_vote);
        System.out.println("in joinVoteActivity");
        Intent intent = getIntent();

        candidates = intent.getExtras().getStringArrayList("candidates");
        name = intent.getExtras().getString("title");

        //System.out.println("voteId_Int: "+ intent.getExtras().getInt("voteid"));
        voteId_Int = intent.getExtras().getInt("voteid");

        //화면 ui들
        VoteTitle = (TextView) findViewById(R.id.voteTitle_join);
        VoteButton = (Button)findViewById(R.id.RegisterVote_join);

        //투표 이름 띄우기
        VoteTitle.setText(name);

        //빈 데이터 리스트 생성.
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice,candidates);

        final ListView listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        //등록 버튼 클릭시 단 한명의 후보 선택시 async 통신을 통해서 참여하게 한다.
        VoteButton.setOnClickListener(new Button.OnClickListener(){

            NetworkTask joinVoteModel = new NetworkTask();
            JSONObject joinVoteJsonObject = new JSONObject();

            String email_Join;
            Integer vote_id_Join;
            Integer candidate_Join = -1;

            @Override
            public void onClick(View v){

                try {
                    SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                    int count = adapter.getCount() ;

                    for (int i = count-1; i >= 0; i--) {
                        if (checkedItems.get(i)) {
                            if( candidate_Join == -1)
                            candidate_Join = i;
                            else {
                                Toast.makeText(joinVoteActivity.this, "choose only one candidate", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                    joinVoteJsonObject.put("email",LoginActivity.EmailPassWord.Email);
                    joinVoteJsonObject.put("vote_id",voteId_Int);
                    joinVoteJsonObject.put("candidate",candidate_Join);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                joinVoteModel.execute(joinVoteJsonObject);

                //클릭후 종료한다.
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();

            }

        });

    }

    //thread for http connection, real http conntection is executed on 'HttpConnectionToServer' class
    public class NetworkTask extends AsyncTask<JSONObject,Void,Boolean> {
        public NetworkTask(){
            ;
        }



        @Override
        protected Boolean doInBackground(JSONObject... params) {
            HttpConnectionToServer ConnectModel = new HttpConnectionToServer();

            if(ConnectModel.JoinVote(params[0])){
                System.out.println("success");
            }
            else{
                System.out.println("failed");

            }



            return true;
        }//doing in background


    }

}
