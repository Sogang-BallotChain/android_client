package com.example.navdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WinnerActivity extends AppCompatActivity {
    String winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        // [yh] 승자정보를 불러오기.
        Intent intent = getIntent();
        winner = intent.getExtras().getString("winner");
        System.out.println(winner);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", winner);
                setResult(RESULT_OK, intent);

                finish();
            }
        });

    }
}
