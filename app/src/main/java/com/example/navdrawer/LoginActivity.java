package com.example.navdrawer;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.widget.Toast.makeText;


public class LoginActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_MENU = 1001;

    EditText emailInput;
    EditText pwInput;

    private static class EmailPassWord{
        String Email;
        String PassWord;

        EmailPassWord(String Email,String PassWord){
            this.Email = Email;
            this.PassWord = PassWord;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startActivity(new Intent(this,SplashActivity.class)); //new Intent는 context와 class를 인자로 받습니다. //만들어둔 SplashActivity를 호출합니다

        Button loginButton = (Button) findViewById(R.id.loginButton);

        emailInput = (EditText) findViewById(R.id.usernameInput);
        pwInput = (EditText) findViewById(R.id.passwordInput);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String emailStr = emailInput.getText().toString();
                String pwStr = pwInput.getText().toString();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("username", emailStr);
                intent.putExtra("password", pwStr);

                LoginActivity.NetworkTask ConnectCreateVoteModel = new LoginActivity.NetworkTask();

                EmailPassWord params =  new EmailPassWord(emailStr,pwStr);
                ConnectCreateVoteModel.execute(params);

                startActivityForResult(intent, REQUEST_CODE_MENU);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE_MENU) {
            if (intent != null) {
                String menu = intent.getStringExtra("menu");
                String message = intent.getStringExtra("message");

                Toast toast = makeText(getBaseContext(), "result code : " + resultCode + ", menu : " + menu + ", message : " + message, Toast.LENGTH_LONG);
                toast.show();
            }
        }

    }

    //thread for http connection, real http conntection is executed on 'HttpConnectionToServer' class
    public class NetworkTask extends AsyncTask<EmailPassWord,Void,Boolean> {
        private String url;
        private ContentValues values;

        public NetworkTask(){
            ;
        }

        @Override
        protected Boolean doInBackground(EmailPassWord... params) {
            HttpConnectionToServer ConnectLoginModel = new HttpConnectionToServer();
            /*
            String email = "pineleaf1216@gmail.com";
            String pass = "12345";
            */
            String email = params[0].Email;
            String pass = params[0].PassWord;



            if(ConnectLoginModel.LogIn(email,pass)) {
                System.out.println("server connected true\n");
                //Toast.makeText(getActivity(), "connected", Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
                System.out.println("server connected false\n");
                //Toast.makeText(getActivity(), "connect failure", Toast.LENGTH_SHORT).show();
                return false;
            }

        }
    }// network task

}
