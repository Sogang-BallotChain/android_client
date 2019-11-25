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

import org.json.JSONObject;

public class joinVote_Fragment extends Fragment {

    private JoinVoteModel joinVoteModel;

    EditText voteId_EditText;
    Button sendId_Button;

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

            System.out.println("response:" + response);
            if(response != "false")
                return true;
            else
                return true;
        }
    }

}