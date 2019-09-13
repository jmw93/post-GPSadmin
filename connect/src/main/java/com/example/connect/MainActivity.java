package com.example.connect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
                if(status == NetworkStatus.TYPE_WIFI)
                    textview.setText("와이파이로 연결됨됨");
                if(status == NetworkStatus.TYPE_MOBILE)
                    textview.setText("모바일로연결됨");
                if(status == NetworkStatus.TYPE_NOT_CONNECTED)
                    textview.setText("연결 안됨");
            }
        });

    }
}
