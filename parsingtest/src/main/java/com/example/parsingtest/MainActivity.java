package com.example.parsingtest;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Trace;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        ArrayList<Hotel> hotellist = new ArrayList<>();
        Hotel hotel;
        URL imgurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                parsing();
                Log.d("jmw93", "총갯수:"+String.valueOf(hotellist.size()));
            }
        }).start();
    }
        public void parsing(){
            String urlrequest ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey=RjzMYQORqJIq4l9YZkCCmV5mTIec%2BdJYC%2BUzK3c2Aogy4I2Y0tZnRI4292OO56Qqr%2FIMajYNHjo5M8Ayz4R05g%3D%3D&contentTypeId=32&areaCode=1&sigunguCode=&cat1=B02&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=O&numOfRows=12&pageNo=1";
            try {
                URL url = new URL(urlrequest);
                InputStream is = url.openStream();
                XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = parserFactory.newPullParser();
                xpp.setInput(new InputStreamReader(is,"UTF-8"));

                int eventType = xpp.getEventType();

                //핵심
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            String startTag = xpp.getName();


                            if(startTag.equals("item")){
                                 hotel= new Hotel();
                                break;
                            }

                            if(startTag.equals("title")){
                                xpp.next();
                                hotel.setTitle(xpp.getText());
                                Log.d("jmw93",xpp.getText());
                                break;
                            }
                            if(startTag.equals("contentid")){
                                xpp.next();
                                Log.d("jmw93",xpp.getText());
                                hotel.setContentid(Integer.parseInt(xpp.getText().toString()));
                                break;
                            }
                            if(startTag.equals("contenttypeid")){

                                xpp.next();
                                Log.d("jmw93",xpp.getText());
                                hotel.setContenttype(Integer.parseInt(xpp.getText().toString()));
                                break;
                            }
                            if(startTag.equals("firstimage")) {
                                xpp.next();
                                if(xpp.getText() !=null ){
                                    try {
                                        Log.d("jmw93",xpp.getText());
                                        imgurl = new URL(xpp.getText());
                                        URLConnection conn = imgurl.openConnection();
                                        conn.connect();
                                        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                                        Bitmap bitmap = BitmapFactory.decodeStream(bis);
                                        bis.close();
                                        hotel.setImage(bitmap);
                                        Log.d("sae1013", "course이미지 로딩");
                                    } catch (Exception e) {
                                        Log.d("jmw93", "이미지로딩실패");
                                    }
                                }
                                break;
                            }



                            break;

                        case XmlPullParser.END_TAG:
                            String endTag =xpp.getName();
                            if(endTag.equals("item")){
                                hotellist.add(hotel);

                            }
                            break;

                    }//switch문의 끝
                    eventType = xpp.next();
                }//for문 끝

            } //try문의 끝
            catch (Exception e){
                Log.e("jmw93",e.toString()+"파싱중오류");
            }

            Log.d("sae1013",hotellist.toString());

        }}





