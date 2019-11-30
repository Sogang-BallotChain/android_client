package com.example.navdrawer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class SeeVoteActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_MENU = 101;

    int voteCode;                   // joinVote_Fragment로 받아온 투표 코드를 저장할 변수.
    String response;                // 서버에서 받아온 raw정보의 string 버전.

    String name;                    // 투표 이름을 저장할 변수.
    ArrayList<String> candidates = new ArrayList<>();   // 후보자를 저장할 array 생성.
    Date start_time = new Date();
    Date end_time = new Date();
    Boolean is_ended = false;       // 일단 false로 초기화.
    String winner;
    ArrayList<String> results = new ArrayList<>();   // 후보자를 저장할 array 생성.

    CandidateAdapter adapter = new CandidateAdapter();  // 어댑터 생성.
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // [yh] 조회api Response 불러오기.
        Intent intent = getIntent();
        voteCode = intent.getExtras().getInt("voteCode");
        System.out.println(voteCode);

        // [yh] xml, 리싸이클러뷰 설정.
        setContentView(R.layout.activity_see_vote);
        recyclerView = findViewById(R.id.recyclerView);

        // [yh] 리싸이클러뷰에 레이아웃 매니저 설정하기.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        // [yh] 네트워크 태스크 실행해서 조회api 사용.
        NetworkTask ConnectSeeVoteModel = new SeeVoteActivity.NetworkTask();
        ConnectSeeVoteModel.execute(voteCode);

        // [yh] 버튼 누를시 승자 확인하는 dialog 띄워줌.
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WinnerActivity.class);
                intent.putExtra("winner", winner);        // [yh] 승자정보를 넘겨주자.
                startActivityForResult(intent, REQUEST_CODE_MENU);
            }
        });

        //adapter.addItem(new Candidate("찬미해", "2"));
        //System.out.println("^^^^^^^^^^^^^^");

    }//onCreate.

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // [yh] dialog 버튼 누르면 승자 토스트메세지로 출력.
        if (requestCode == REQUEST_CODE_MENU) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra("name");
                Toast.makeText(getApplicationContext(), "The winner is " + name + "!",
                        Toast.LENGTH_LONG).show();
            }
        }

    }

    //thread for http connection, real http conntection is executed on 'HttpConnectionToServer' class
    public class NetworkTask extends AsyncTask<Integer,Void,ArrayList<String>> {
        public NetworkTask(){
            ;
        }

        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            HttpConnectionToServer ConnectModel = new HttpConnectionToServer();
            response = ConnectModel.GetVoteInfomation(params[0]);
            try {
                JSONObject jsonObject = new JSONObject(response);   // [yh] 최외각 JSON 객체.
                JSONObject data = jsonObject.getJSONObject("data"); // [yh] 안쪽 data JSON 객체.

                // [yh] 투표이름 추출.
                name = data.getString("name");
                System.out.println("name : " + name);

                // [yh] 후보array 추출.
                JSONArray candidate_list = (JSONArray) data.get("candidate_list");  // [dh] JSON array에 값 받아오기.
                System.out.println("data : "+data.getString("candidate_list"));
                for (int i = 0; i < candidate_list.length(); i++) {
                    candidates.add(candidate_list.get(i).toString()); // [dh] JSON array에 후보자 차례로 저장.
                    System.out.println(candidates.get(i));
                }

                // [yh] 시작시간, 종료시간 추출.  ////////////////////////////////////////

                // [yh] 종료여부 추출.
                is_ended = data.getBoolean("is_ended"); // 종료여부 assign.
                System.out.println("is_ended : " + is_ended);

                // [yh] 승자 추출.
                winner = data.getString("winner");
                System.out.println("winner : " + winner);

                // [yh] 후보별 득표수 추출.
                JSONObject data2 = data.getJSONObject("result");
                for (int i = 0; i < candidate_list.length(); i++) {
                    results.add( String.valueOf ( data2.getInt(candidate_list.get(i).toString()) ) );
                    System.out.println(results.get(i));
                }

            }
            catch (JSONException e) {
                ;
            }
            return candidates;
        }//doing in background

        @Override
        protected void onPostExecute(ArrayList<String> candidates){
            super.onPostExecute(candidates);
            System.out.println("candidates.size : " + candidates.size());
            // [yh] 코드 시작부에 선언된 candidates는 hiding되었지만, 사실 그 변수를 사용해도 무방.
            for (int i = 0; i < candidates.size(); i++) {
                adapter.addItem(new Candidate(candidates.get(i), results.get(i)));
                System.out.println(candidates.get(i) + " " + results.get(i) + " 표");
            }

            // [yh] 리싸이클러뷰 어댑터 설정하기.
            recyclerView.setAdapter(adapter);

        }//onPostExecute.

    }//NetworkTask.
}
