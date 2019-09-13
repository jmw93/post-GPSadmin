package com.example.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.fragment.Gps_btn3.MapsActivity;
import com.example.fragment.Naver_NAVI_btn4.MyFragment3;
import com.example.fragment.language_btn2.lang_fragment;
import com.example.fragment.main_View_btn1.webViewActivity;
import com.example.fragment.main_View_btn1.MainFragment;
import com.example.fragment.parse_Tour.Tour_informActivity;
import com.example.fragment.parse_course.CourseInfoActivity;
import com.example.fragment.parse_hotel.HotelInfoActivity;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements onwebListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomBar);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new MainFragment()).commit();//첫화면설정

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
                switch(Item.getItemId()){
                    case R.id.bottom_home:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new MainFragment()).commit();
                        break;
                    }
                    case R.id.bottom_tran:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new lang_fragment()).commit();
                        break;
                    }
                    case R.id.bottom_tour:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new mainViewPagerfragment()).commit();
                        break;
                    }
                    case R.id.bottom_gps:{
                        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
                return true;
            }
        });
    }


    @Override
    public void sendwebView(String URL) {
        Intent intent = new Intent(this, webViewActivity.class);
        intent.putExtra("URL", URL);
        startActivity(intent);
    } // 메인화면에서 클릭시 웹 액티비티 띄워주는것

    public void sendcontenttypeid_tour(int contentid,int contenttypeid){
        Intent intent = new Intent(getApplicationContext(), Tour_informActivity.class);
        intent.putExtra("contentid",contentid);
        intent.putExtra("contenttypeid",contenttypeid);
        startActivity(intent);
    }
    public void sendcontenttypeid_course(int contentid, int contenttypeid){
        Intent intent = new Intent(getApplicationContext(),CourseInfoActivity.class);
        intent.putExtra("contentid",contentid);
        intent.putExtra("contenttypeid",contenttypeid);
        startActivity(intent);
    }

    //호텔에서 상세정보 요청보낼때
    public void sendcontenttypeid_hotel(int contentid, int contenttypeid){
        Intent intent = new Intent(getApplicationContext(),HotelInfoActivity.class);
        intent.putExtra("contentid",contentid);
        intent.putExtra("contenttypeid",contenttypeid);
        startActivity(intent);
    }


}