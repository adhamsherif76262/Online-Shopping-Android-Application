package com.example.online_shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.online_shopping.MyDatabase;
import com.example.online_shopping.Model.CustomerModel;
import com.example.online_shopping.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class signupActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();

    EditText username , email,password,Birthday,job;
    Button signup_btn;
    EditText gender;
    MyDatabase database;
    DatePickerDialog.OnDateSetListener date;
//    CalendarView calender = (CalendarView) findViewById(R.id.calendarView2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username=findViewById(R.id.username_signup);
        email=findViewById(R.id.email_signup);
        password=findViewById(R.id.password_signup);

        Birthday=findViewById(R.id.Birthday);
        job=findViewById(R.id.job);
        gender=findViewById(R.id.gender);
        signup_btn=findViewById(R.id.btn_signup);
        database=new MyDatabase(this);

//        calender.setVisibility(View.INVISIBLE);


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        Birthday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(signupActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

//        Birthday.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                calender.setVisibility(View.VISIBLE);
//            }
//        });

        CalendarView C = (CalendarView) findViewById(R.id.calendarView3);
        C.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String Date = i2 + "/" + (i1+1) + "/" + i;
                Birthday.setText(Date);
            }
        });



        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Birthday.setText(sdf.format(myCalendar.getTime()));
    }

    protected void signUp() {
        String name = username.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String gen = gender.getText().toString();
        String birthdate =  Birthday.getText().toString();
        String joptitle=job.getText().toString();

        if (name.equals("") || mail.equals("") || pass.equals(""))
            Toast.makeText(this, "Enter All Data,Please", Toast.LENGTH_SHORT).show();
        else {
            CustomerModel customerModel=new CustomerModel(name,mail,pass,gen,joptitle,birthdate);
            database.insertCustomer(customerModel);
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(signupActivity.this, loginActivity.class);
            startActivity(intent);
            finish();
        }


    }
}