package com.example.navdrawer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.navdrawer.ui.create_vote.create_vote;
import com.example.navdrawer.ui.create_account.createAccount_Fragment;
import com.example.navdrawer.ui.join_vote.joinVote_Fragment;
import com.example.navdrawer.ui.seeThrough_vote.seeThrough_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    String password;

    public static class EmailPassWord{
        public static String Email;
        public static String PassWord;

        EmailPassWord(String Email,String PassWord){
            this.Email = Email;
            this.PassWord = PassWord;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // [yh] LoginActivity로부터 정보 받기.
        Intent intent = getIntent();
        email = intent.getExtras().getString("email");
        password = intent.getExtras().getString("password");

        NetworkTask ConnectCreateVoteModel = new NetworkTask();
        EmailPassWord params = new EmailPassWord(email, password);

        Button logoutButton = findViewById(R.id.logoutButton); // 의미상 submitButton이 바르지만, dependency 문제로 보류.
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NetworkTask ConnectCreateVoteModel = new NetworkTask();
//                String birthStr = birthButton.getText().toString();
                EmailPassWord params = new EmailPassWord(email, password);
                ConnectCreateVoteModel.execute(params);
                //   Toast toast = Toast.makeText(getBaseContext(), emailStr + " 님, 회원가입 완료!", Toast.LENGTH_LONG);
                //   toast.show();

                // [yh] 디버깅용 코드.
                //Toast.makeText(getApplicationContext(), "이메일 : " + emailStr + ", 비밀번호 : " + pwStr, Toast.LENGTH_SHORT).show();
/*
                // [yh] onPostExecute에서 처리하도록 수정.
                Intent resultIntent = new Intent();
                resultIntent.putExtra("email", emailStr);
                resultIntent.putExtra("password", pwStr);

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
 */
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

    public class NetworkTask extends AsyncTask<EmailPassWord,Void,Boolean> {
        private String url;
        private ContentValues values;

        public NetworkTask(){
            ;
        }

        @Override
        protected Boolean doInBackground(EmailPassWord... params) {
            HttpConnectionToServer ConnectCreateVoteModel = new HttpConnectionToServer();
            /*
            String email = "pineleaf1216@gmail.com";
            String pass = "12345";
            */
            String email = params[0].Email;
            String pass = params[0].PassWord;

            if(ConnectCreateVoteModel.LogOut(email,pass)) {
                System.out.println("server connected true\n");
                // [yh] 토스트 실행시 에러.
                //Toast.makeText(getApplicationContext(), "connected", Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
                System.out.println("server connected false\n");
                // [yh] 토스트 실행시 에러.
                //Toast.makeText(getActivity(), "connect failure", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            System.out.println("on Post Execute !!\n");

            // [yh] 중복된 이메일인 경우 후속처리.
            if (!result) {
                //Toast toast = Toast.makeText(getBaseContext(), "이미 가입하셨습니다!", Toast.LENGTH_LONG);
                Toast.makeText(getApplicationContext(), email + " 님, 로그아웃 실패!", Toast.LENGTH_SHORT).show();
                //        Intent intent = getIntent();
                //        finish();
                //startActivity(intent);
                //        startActivityForResult(intent, REQUEST_CODE_MENU);
            }
            // [yh] 다른 예외가 없는 경우 액티비티 종료.
            else if (result) {
                Toast.makeText(getApplicationContext(), email + " 님의 로그아웃이 완료됐습니다!", Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        }

    }//networktask
}
