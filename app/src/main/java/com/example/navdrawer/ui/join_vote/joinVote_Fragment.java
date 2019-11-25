package com.example.navdrawer.ui.join_vote;

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
import com.example.navdrawer.ui.create_vote.create_vote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;

public class joinVote_Fragment extends Fragment {

    private JoinVoteModel joinVoteModel;

    EditText voteId_EditText;
    Button sendId_Button;


    //for responsed datas
    ArrayList<String> candidates = new ArrayList<>();

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
       //         voteId_Int = Integer.parseInt(voteId_string);
                String result;
                voteId_Int = 47;

                System.out.println("voteId_Int: "+ voteId_Int);

                ConnectCreateVoteModel.execute(voteId_Int);
            }

        });

        return root;
    }

    //thread for http connection, real http conntection is executed on 'HttpConnectionToServer' class
    public class NetworkTask extends AsyncTask<Integer,Void,Boolean> {
        public NetworkTask(){
            ;
        }



        @Override
        protected Boolean doInBackground(Integer... params) {
            HttpConnectionToServer ConnectModel = new HttpConnectionToServer();
            String response = ConnectModel.GetVoteInfomation(params[0]);

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject data = jsonObject.getJSONObject("data");
                //여기서 json parsing

                //candidates = (ArrayList<String>) data.get("candidate_list");
                JSONArray candidate_list = (JSONArray) data.get("candidate_list");

                String name;

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

            }
            //System.out.println("response:" + response);
            if(response != "false")
                return true;
            else
                return true;
        }
    }

}