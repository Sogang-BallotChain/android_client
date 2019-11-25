package com.example.navdrawer.ui.create_account;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.navdrawer.HttpConnectionToServer;
import com.example.navdrawer.R;

import java.util.Date;

//kdh


public class createAccount_Fragment extends Fragment {
    EditText emailInput;
    EditText pwInput;

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

    public createAccount_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);


        emailInput = rootView.findViewById(R.id.emailInput);
        pwInput = rootView.findViewById(R.id.pwInput);

 //       birthButton = rootView.findViewById(R.id.birthButton);
 //       birthButton.setOnClickListener(new View.OnClickListener() {
 //           public void onClick(View v) {
 //               showDateDialog();
 //           }
 //       });

        Button saveButton = rootView.findViewById(R.id.loginButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NetworkTask ConnectCreateVoteModel = new NetworkTask();
                String emailStr = emailInput.getText().toString();
                String pwStr = pwInput.getText().toString();
//                String birthStr = birthButton.getText().toString();
                EmailPassWord params =  new EmailPassWord(emailStr,pwStr);
                ConnectCreateVoteModel.execute(params);
                Toast.makeText(getContext(), "이메일 : " + emailStr + ", 비밀번호 : " + pwStr, Toast.LENGTH_SHORT).show();
            }
        });


        // set selected date using current date
        Date curDate = new Date();
 //       setSelectedDate(curDate);



        return rootView;
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
                Toast.makeText(getActivity(), "connected", Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
                System.out.println("server connected false\n");
                //Toast.makeText(getActivity(), "connect failure", Toast.LENGTH_SHORT).show();
                return false;
            }


        }
    }//networktask

}
