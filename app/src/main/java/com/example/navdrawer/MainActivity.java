package com.example.navdrawer;

import android.content.Intent;
import android.os.Bundle;

import com.example.navdrawer.ui.create_vote.create_vote;
import com.example.navdrawer.ui.create_account.createAccount_Fragment;
import com.example.navdrawer.ui.join_vote.joinVote_Fragment;
import com.example.navdrawer.ui.seeThrough_vote.seeThrough_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private create_vote frag1;
    private joinVote_Fragment frag2;
    private seeThrough_Fragment frag3;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // [yh] LoginActivity로부터 정보 받기.
        Intent intent = getIntent();
        email = intent.getExtras().getString("email");

        ConstraintLayout drawer = findViewById(R.id.main_layout);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavi);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.action_today:
                        setFrag(0);
                        break;
                    case R.id.action_add:
                        setFrag(1);
                        break;
                    case R.id.action_setting:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });

        frag1=new create_vote();
        frag2=new joinVote_Fragment();
        frag3=new seeThrough_Fragment();
        setFrag(0); // 첫 프래그먼트 화면 지정

    }//OnCreate


    // 프레그먼트 교체
    private void setFrag(int n)
    {
        fm = getSupportFragmentManager();
        ft= fm.beginTransaction();
        switch (n)
        {
            case 0:
                ft.replace(R.id.Main_Frame,frag1);
                ft.commit();
                break;

            case 1:
                ft.replace(R.id.Main_Frame,frag2);
                ft.commit();
                break;

            case 2:
                ft.replace(R.id.Main_Frame,frag3);
                ft.commit();
                break;


        }
    }
}
