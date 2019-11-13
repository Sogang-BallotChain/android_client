package com.example.navdrawer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.navdrawer.ui.create_vote.create_vote;
import com.example.navdrawer.ui.home.HomeFragment;
import com.example.navdrawer.ui.join_vote.join_vote;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private create_vote frag1;
    private HomeFragment frag2;
    private join_vote frag3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("menu", "고객관리 메뉴");
                resultIntent.putExtra("message", "result message is OK!");

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

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
        frag2=new HomeFragment();
        frag3=new join_vote();
        setFrag(0); // 첫 프래그먼트 화면 지정
    }

        /*
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
         */

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
