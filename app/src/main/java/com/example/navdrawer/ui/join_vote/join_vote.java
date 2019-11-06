package com.example.navdrawer.ui.join_vote;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.navdrawer.R;
import com.example.navdrawer.SeeDiscriptionAndJoinVote;

public class join_vote extends Fragment {

    private JoinVoteModel joinVoteModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        joinVoteModel =
                ViewModelProviders.of(this).get(JoinVoteModel.class);
        View root = inflater.inflate(R.layout.join_vote, container, false);
        /*
        final TextView textView = root.findViewById(R.id.text_slideshow);
        joinVoteModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */


        //push Button and move to SeeDiscriptionAndJOinVote
        Button intentButton = root.findViewById(R.id.create_vote_button);
        intentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity() , SeeDiscriptionAndJoinVote.class);
                startActivity(intent);
            }
        } );

        return root;
    }
}