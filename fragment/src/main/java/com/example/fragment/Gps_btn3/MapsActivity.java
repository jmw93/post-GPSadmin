package com.example.fragment.Gps_btn3;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.StrictMode;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment.R;
import com.google.android.gms.location.LocationRequest;

public class MapsActivity extends AppCompatActivity {
    LocationRequest request;
    EditText edit_Id;
    EditText edit_password;
    EditText edit_recipient;
    TextView text_state;
    RadioGroup radioGroup;
    String settingtime;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mContext = getApplicationContext();
        radioGroup = findViewById(R.id.radiogroup);
        text_state =findViewById(R.id.state);
        edit_Id = (EditText) findViewById(R.id.edit_Id);
        edit_password = (EditText) findViewById(R.id.edit_Password);
        edit_recipient = (EditText) findViewById(R.id.edit_recipient);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());
        backUpData();

        Button btn_stop = findViewById(R.id.btn_stop);
        Button btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onstartService();  // 서비스실행메소드 실행
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onstopService(); //서비스중지메소드실행
            }
        });


    }


    private void backUpData() {
        SharedPreferences pref = getSharedPreferences("sFile",MODE_PRIVATE);
        String Id=pref.getString("Id","");
        String password=pref.getString("password","");
        String recipient =pref.getString("recipient","");
        String state=pref.getString("state","OFF");
        edit_Id.setText(Id);
        edit_password.setText(password);
        edit_recipient.setText(recipient);
        text_state.setText(state);

    }
        //서비스시작
    private void onstartService() {
            String emailAddr = edit_Id.getText().toString();
            String emailPassword = edit_password.getText().toString();
            String recipient = edit_recipient.getText().toString();
            int id=radioGroup.getCheckedRadioButtonId();
            RadioButton rb = findViewById(id);
            if(rb == null){
                Toast.makeText(this, "Select Time", Toast.LENGTH_SHORT).show();
            }else {
                settingtime = rb.getText().toString();
            }
        if(emailAddr!=null && emailPassword !=null && recipient !=null && settingtime !=null) {

            text_state.setText("ON");
            text_state.setTextColor(Color.parseColor("#04B404"));
            Intent intent = new Intent(this, MyService.class);
            intent.putExtra("emailAddr", emailAddr);
            intent.putExtra("emailPassword", emailPassword);
            intent.putExtra("recipient", recipient);
            intent.putExtra("settingtime",settingtime);
            startService(intent);
            }else{
            Toast.makeText(this, "모두 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        }

    private void onstopService() {
        text_state.setText("OFF");
        text_state.setTextColor(Color.parseColor("#DF0101"));
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    @Override
    protected void onPause() {

        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences pref = getSharedPreferences("sFile",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String Id = edit_Id.getText().toString();
        String password = edit_password.getText().toString();
        String recipient = edit_recipient.getText().toString();
        String state=text_state.getText().toString();
        editor.putString("Id", Id);
        editor.commit();
        editor.putString("password", password);
        editor.commit();
        editor.putString("recipient", recipient);
        editor.commit();
        editor.putString("state",state);
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle", "onDestroy실행");
    }



}
