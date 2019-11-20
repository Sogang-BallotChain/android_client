package com.example.navdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navdrawer.ui.create_vote.create_vote;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddCandidates extends AppCompatActivity {
    EditText edittext;
    Button buttonAdd;
    ArrayList<String> items;
    ArrayAdapter adapter;

    @Override
    public void onBackPressed() {
        //버튼 클릭시 리스트에 추가.
        Intent intent = new Intent();
        intent.putExtra("items",items);
        setResult(RESULT_OK,intent);
        finish();

       // super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_discription_and_join_vote);

        //화면 ui들
        edittext = (EditText)findViewById(R.id.edit_candidates);
        buttonAdd = (Button)findViewById(R.id.AddCandidateButton);
        Button buttonDelete = (Button)findViewById(R.id.DeleteButtonCandidates);
        Button buttonRegister = (Button)findViewById(R.id.RegisterButtonCandidates);
        //빈 데이터 리스트 생성.
        items = new ArrayList<String>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice,items);

        final ListView listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        //버튼 클릭시 리스트에 추가.
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Editable Text1 = edittext.getText();
                items.add(Text1.toString());
                adapter.notifyDataSetChanged();
            }
        });


        buttonDelete.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                int count = adapter.getCount() ;

                for (int i = count-1; i >= 0; i--) {
                    if (checkedItems.get(i)) {
                        items.remove(i) ;
                    }
                }

                // 모든 선택 상태 초기화.
                listview.clearChoices() ;

                adapter.notifyDataSetChanged();
            }

        }) ;

        buttonRegister.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v){

                Intent intent = new Intent();
                intent.putExtra("items",items);
                setResult(RESULT_OK,intent);
                finish();

            }

        });


    }


}
