package com.example.navdrawer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SeeVoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//System.out.println("---------");
/*
        setContentView(R.layout.activity_see_vote);
System.out.println("========");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
System.out.println("%%%%%%%%%%%%%%%%");

        recyclerView.setLayoutManager(layoutManager);
System.out.println("#############");

        CandidateAdapter adapter = new CandidateAdapter();
System.out.println("$$$$$$$$$$$$$$");
*/
        setContentView(R.layout.activity_see_vote);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        CandidateAdapter adapter = new CandidateAdapter();
        recyclerView.setAdapter(adapter);

        adapter.addItem(new Candidate("김민수", 1));
        adapter.addItem(new Candidate("찬미해", 2));
        adapter.addItem(new Candidate("찬양해", 3));
//System.out.println("^^^^^^^^^^^^^^");

    }
}
