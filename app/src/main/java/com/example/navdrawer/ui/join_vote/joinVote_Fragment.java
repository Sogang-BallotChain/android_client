package com.example.navdrawer.ui.join_vote;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.navdrawer.HttpConnectionToServer;
import com.example.navdrawer.R;
import com.example.navdrawer.joinVoteActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class joinVote_Fragment extends Fragment {

    private JoinVoteModel joinVoteModel;

    EditText voteId_EditText;
    Button sendId_Button;


    //for responsed datas

    final int REQUEST_TEST = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        joinVoteModel =
                ViewModelProviders.of(this).get(JoinVoteModel.class);
        View root = inflater.inflate(R.layout.join_vote, container, false);


        voteId_EditText = root.findViewById(R.id.vote_Id);
        sendId_Button = root.findViewById(R.id.get_voteId_button);


        sendId_Button.setOnClickListener(new View.OnClickListener(){
            String voteId_string;
            int voteId_Int;

            NetworkTask ConnectCreateVoteModel = new joinVote_Fragment.NetworkTask();
            @Override
            public void onClick(View v) {
                //for test not implemented list adding ui
                voteId_string =  voteId_EditText.getText().toString();
                voteId_Int = Integer.parseInt(voteId_string);   // [yh] 투표코드 assign.
        //        String result;
        //        voteId_Int = 47;

                System.out.println("voteId_Int: "+ voteId_Int);
                // [dh] 새로 만든 네트워크 스레드에서 get요청과 제이슨 파싱을 실행시킨다.
                ConnectCreateVoteModel.execute(voteId_Int);


            }


        });




        return root;
    }

    //thread for http connection, real http conntection is executed on 'HttpConnectionToServer' class
    public class NetworkTask extends AsyncTask<Integer,Void,ArrayList<String>> {
        public NetworkTask(){
            ;
        }

        String name;    // 투표 이름을 저장할 변수.

        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            HttpConnectionToServer ConnectModel = new HttpConnectionToServer();
            String response = ConnectModel.GetVoteInfomation(params[0]);
            ArrayList<String> candidates = new ArrayList<>();   // 후보자를 저장할 array 생성.
            try {
                JSONObject jsonObject = new JSONObject(response);   // 최외각 JSON 객체.
                JSONObject data = jsonObject.getJSONObject("data"); // 안쪽 data JSON 객체.
                //여기서 json parsing

                name = data.getString("name");  // 투표이름 assign.
                System.out.println(name);

                //candidates = (ArrayList<String>) data.get("candidate_list");
                JSONArray candidate_list = (JSONArray) data.get("candidate_list");  // JSON array에 값 받아오기.
                for(int i=0;i<candidate_list.length();i++ )
                {
                    candidates.add( candidate_list.get(i).toString() ); // JSON array에 후보자 차례로 저장.
                //    System.out.println(candidate_list.get(i));
                    System.out.println(candidates.get(i));
                }
            /*
                System.out.println("data "+data.getString("candidate_list"));
                JSONObject candidate_list_json = data.optJSONObject("candidate_list");
                System.out.println("candidate_list "+ candidate_list_json);
            */
            }
            catch ( JSONException e){
                    ;
            }
            return candidates;
        }//doing in background

        @Override
        protected void onPostExecute(ArrayList<String>candidates){
            super.onPostExecute(candidates);

            Intent intent = new Intent(getActivity() , joinVoteActivity.class);
            intent.putExtra("candidates",candidates);
            intent.putExtra("title",name);
            startActivityForResult(intent,REQUEST_TEST);
        }//on Post Execute.

    }//Network task.

}