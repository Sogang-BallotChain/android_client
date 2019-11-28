package com.example.navdrawer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;

import static android.text.TextUtils.*;
import static com.example.navdrawer.LoginActivity.REQUEST_CODE_MENU;

public class CreateAccountActivity extends AppCompatActivity {
    EditText emailInput;
    EditText pwInput;
    EditText pwInput2;

    //kdh
    //object email , password , passing to asynctask
    private static class EmailPassWord{
        String Email;
        String PassWord;

        EmailPassWord(String Email,String PassWord){
            this.Email = Email;
            this.PassWord = PassWord;
        }
    }

//    Button birthButton;

//    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

    Date selectedDate;
/*
    public CreateAccountActivity() {

    }
*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        setContentView(R.layout.activity_create_account);

        emailInput = findViewById(R.id.emailInput);
        pwInput = findViewById(R.id.pwInput);
        pwInput2 = findViewById(R.id.pwInput2);

        //       birthButton = rootView.findViewById(R.id.birthButton);
        //       birthButton.setOnClickListener(new View.OnClickListener() {
        //           public void onClick(View v) {
        //               showDateDialog();
        //           }
        //       });

        // [yh] 돌아가기 버튼.
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        Button saveButton = findViewById(R.id.loginButton); // 의미상 submitButton이 바르지만, dependency 문제로 보류.
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NetworkTask ConnectCreateVoteModel = new NetworkTask();
                String emailStr = emailInput.getText().toString();
                String pwStr = pwInput.getText().toString();
                String pwStr2 = pwInput2.getText().toString();
//                String birthStr = birthButton.getText().toString();
                EmailPassWord params = new EmailPassWord(emailStr, pwStr);

                // [yh] 비번, 비번확인 일치여부 검사.
                if (!pwStr.equals(pwStr2) || isEmpty(emailStr) || isEmpty(pwStr) || isEmpty(pwStr2)) {
                    Toast.makeText(getApplicationContext(), "이메일과 비밀번호와 비번확인을 정확히 입력해주세요!", Toast.LENGTH_SHORT).show();
                //    Intent intent = getIntent();
                //    finish();
                    //startActivity(intent);
                //    startActivityForResult(intent, REQUEST_CODE_MENU);
                }
                else {
                    ConnectCreateVoteModel.execute(params);
                 //   Toast toast = Toast.makeText(getBaseContext(), emailStr + " 님, 회원가입 완료!", Toast.LENGTH_LONG);
                 //   toast.show();
                }

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

        // set selected date using current date
        Date curDate = new Date();
        //       setSelectedDate(curDate);

        // return rootView;
    }//oncreate

    //thread for http connection, real http conntection is executed on 'HttpConnectionToServer' class
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

            if(ConnectCreateVoteModel.CreateAccount(email,pass)) {
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
                Toast.makeText(getApplicationContext(), "이미 가입된 이메일입니다!", Toast.LENGTH_SHORT).show();
        //        Intent intent = getIntent();
        //        finish();
                //startActivity(intent);
        //        startActivityForResult(intent, REQUEST_CODE_MENU);
            }
            // [yh] 다른 예외가 없는 경우 액티비티 종료.
            else if (result) {
                Toast.makeText(getApplicationContext(), "회원가입이 완료됐습니다 :)", Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        }

    }//networktask

}
