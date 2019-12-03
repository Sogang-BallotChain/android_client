package com.example.navdrawer.ui.seeThrough_vote;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.navdrawer.HttpConnectionToServer;
import com.example.navdrawer.R;
import com.example.navdrawer.SeeVoteActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class seeThrough_Fragment extends Fragment {

    private seeThrough_ViewModel toolsViewModel;

    EditText voteID_ET;
    Button sendID_BT;

    //for responsed datas

    final int REQUEST_TEST = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(seeThrough_ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        voteID_ET = root.findViewById(R.id.voteID_);
        sendID_BT = root.findViewById(R.id.send_voteID_button_);

        sendID_BT.setOnClickListener(new View.OnClickListener(){
            String voteID_Str;
            int voteID_Int;

            NetworkTask ConnectSeeThroughModel = new seeThrough_Fragment.NetworkTask();
            @Override
            public void onClick(View v) {
                //for test not implemented list adding ui
                voteID_Str =  voteID_ET.getText().toString();
                voteID_Int = Integer.parseInt(voteID_Str);      // [yh] 투표코드 assign.
            //    voteID_Int = 47;    //////////////////////////// 테스트용.
            //    String result;

                System.out.println("voteID_Int: "+ voteID_Int);
                // [dh] 새로 만든 네트워크 스레드에서 get요청과 제이슨 파싱을 실행시킨다.
                ConnectSeeThroughModel.execute(voteID_Int);
            }
        });//setOnClickListener

        return root;
    }//onCreateView


    //thread for http connection, real http conntection is executed on 'HttpConnectionToServer' class
    public class NetworkTask extends AsyncTask<Integer,Void, ArrayList<String>> {
        public NetworkTask(){
            ;
        }

        int voteID;         // 투표코드 저장용 변수.
        String response;    // 서버에서 받아온 raw정보의 string 버전.
        String name;
        Boolean is_ended = false;   // 일단 false로 초기화.

        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            HttpConnectionToServer ConnectModel = new HttpConnectionToServer();
            response = ConnectModel.GetVoteInfomation(params[0]);
            voteID = params[0]; // [yh] 추가.

            ArrayList<String> candidates = new ArrayList<>();   // 후보자를 저장할 array 생성.
            try {
                //System.out.println(response);
                // [yh] 투표코드가 유효할 때만 수행.
                if (!response.equals("empty")) {
                    JSONObject jsonObject = new JSONObject(response);   // 최외각 JSON 객체.
                    JSONObject data = jsonObject.getJSONObject("data"); // 안쪽 data JSON 객체.
                    //여기서 json parsing

                    name = data.getString("name");
                    System.out.println(name);

                    //candidates = (ArrayList<String>) data.get("candidate_list");
                    JSONArray candidate_list = (JSONArray) data.get("candidate_list");
                    for (int i = 0; i < candidate_list.length(); i++) {
                        candidates.add(candidate_list.get(i).toString());
                        System.out.println(candidate_list.get(i));
                        System.out.println(candidates.get(i));
                    }

                    is_ended = data.getBoolean("is_ended"); // 종료여부 assign.
                    System.out.println(is_ended);
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
        }//doInBackground

        @Override
        protected void onPostExecute(ArrayList<String>candidates){
            super.onPostExecute(candidates);

            // [yh] 투표코드 자체를 잘못 입력한 경우.
            if (response.equals("empty")) {
                Toast.makeText(getContext(), "투표코드를 올바르게 입력해주세요!", Toast.LENGTH_SHORT).show();
            }
            else {
                // [yh] 종료된 투표인 경우.
                if (is_ended) {
                    Intent intent = new Intent(getActivity(), SeeVoteActivity.class);  // [yh] 투표결과조회.
                    intent.putExtra("voteCode", voteID);        // [yh] 투표 코드를 넘겨주자.
                    startActivityForResult(intent, REQUEST_TEST);
                }
                // [yh] 종료되지 않은 투표인 경우.
                else {
                    Toast.makeText(getContext(), "현재 진행중인 투표의 결과는 확인할 수 없습니다!", Toast.LENGTH_SHORT).show();
                }
            }

        }//on Post Execute.

    }//NetworkTask

}