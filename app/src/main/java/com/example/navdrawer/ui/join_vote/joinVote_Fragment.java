package com.example.navdrawer.ui.join_vote;

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
import com.example.navdrawer.joinVoteActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class joinVote_Fragment extends Fragment {

    private JoinVoteModel joinVoteModel;

    EditText voteId_EditText;
    Button sendId_Button;
    Integer voteId_Int;

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



            @Override
            public void onClick(View v) {
                NetworkTask ConnectCreateVoteModel = new joinVote_Fragment.NetworkTask();
                //for test not implemented list adding ui
                //voteId_string =  voteId_EditText.getText().toString();
                //         voteId_Int = Integer.parseInt(voteId_string);
                String result;
                voteId_Int = Integer.parseInt(voteId_EditText.getText().toString());
                System.out.println("voteId_Int: "+ voteId_Int);
                //새로 만든 네트워크 스레드에서 get요청과 제이슨 파싱을 실행시킨다.
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

        String name;



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

                System.out.println("names: "+name);

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
        }//doing in background

        @Override
        protected void onPostExecute(ArrayList<String>candidates){
            super.onPostExecute(candidates);


            Intent intent = new Intent(getActivity() , joinVoteActivity.class);
            intent.putExtra("candidates",candidates);
            intent.putExtra("title",name);

            System.out.println("before intent voteId_Int: "+ voteId_Int);
            
            startActivityForResult(intent,REQUEST_TEST);

        }

    }

}