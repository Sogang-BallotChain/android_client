package com.example.navdrawer.ui.join_vote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.navdrawer.R;

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




        return root;
    }
}