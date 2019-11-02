package com.example.navdrawer.ui.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.navdrawer.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {
    EditText nameInput;
    EditText emailInput;

//    Button birthButton;

//    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

    Date selectedDate;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);


        nameInput = rootView.findViewById(R.id.nameInput);
        emailInput = rootView.findViewById(R.id.emailInput);

 //       birthButton = rootView.findViewById(R.id.birthButton);
 //       birthButton.setOnClickListener(new View.OnClickListener() {
 //           public void onClick(View v) {
 //               showDateDialog();
 //           }
 //       });

        Button saveButton = rootView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nameStr = nameInput.getText().toString();
                String emailStr = emailInput.getText().toString();
//                String birthStr = birthButton.getText().toString();

                Toast.makeText(getContext(), "이름 : " + nameStr + ", 이메일 : " + emailStr, Toast.LENGTH_SHORT).show();
            }
        });


        // set selected date using current date
        Date curDate = new Date();
 //       setSelectedDate(curDate);

        return rootView;
    }

}
