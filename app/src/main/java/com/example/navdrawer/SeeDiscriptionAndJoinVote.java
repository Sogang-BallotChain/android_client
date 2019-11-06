package com.example.navdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SeeDiscriptionAndJoinVote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_discription_and_join_vote);


        //빈 데이터 리스트 생성.
        final ArrayList<String> items = new ArrayList<String>();
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice,items);

        final ListView listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        //item추가
        int count;
        for(int i=0;i<4;i++) {
            count = adapter.getCount();
            items.add("List" + Integer.toString(count + 1));
          //  Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        }
        //listview 갱신
        adapter.notifyDataSetChanged();


    }


}
