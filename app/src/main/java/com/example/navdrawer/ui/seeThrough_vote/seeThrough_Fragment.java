package com.example.navdrawer.ui.seeThrough_vote;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.navdrawer.HttpConnectionToServer;
import com.example.navdrawer.R;
import com.example.navdrawer.joinVoteActivity;
import com.example.navdrawer.ui.join_vote.joinVote_Fragment;

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

/*
        final TextView textView = root.findViewById(R.id.text_tools);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
*/
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
                //         voteId_Int = Integer.parseInt(voteId_string);
                String result;
                voteID_Int = Integer.parseInt(voteID_Str);

                //voteID_Int = 47;    //////////////////////////// 테스트용.

                System.out.println("voteId_Int: "+ voteID_Int);
                // 새로 만든 네트워크 스레드에서 get요청과 제이슨 파싱을 실행시킨다!
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

        String name;

        @Override
        protected void onPostExecute(ArrayList<String>candidates){
            super.onPostExecute(candidates);

            Intent intent = new Intent(getActivity() , joinVoteActivity.class); //////// 수정!!!!!!!!!!!!
            intent.putExtra("candidates",candidates);
            intent.putExtra("title",name);
            startActivityForResult(intent,REQUEST_TEST);
        }

        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            HttpConnectionToServer ConnectModel = new HttpConnectionToServer();
            String response = ConnectModel.GetVoteInfomation(params[0]);
            ArrayList<String> candidates = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject data = jsonObject.getJSONObject("data");

                //여기서 json parsing

                //candidates = (ArrayList<String>) data.get("candidate_list");
                JSONArray candidate_list = (JSONArray) data.get("candidate_list");

                name = data.getString("name");
                System.out.println(name);

                for(int i=0;i<candidate_list.length();i++ )
                {
                    candidates.add( candidate_list.get(i).toString() );
                    System.out.println(candidate_list.get(i));
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
        }//doInBackground

    }//NetworkTask

}