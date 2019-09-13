package com.example.fragment.parse_course;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.example.fragment.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class CourseInfoActivity extends AppCompatActivity {
    int contentid;
    String Key="RjzMYQORqJIq4l9YZkCCmV5mTIec%2BdJYC%2BUzK3c2Aogy4I2Y0tZnRI4292OO56Qqr%2FIMajYNHjo5M8Ayz4R05g%3D%3D";
    private String requesturl;
    course_info course;
    ArrayList<course_info> courselist = new ArrayList<>();
    Adapter2 Adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courseinfo);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        Intent intent = getIntent();
       contentid= intent.getExtras().getInt("contentid");
       requesturl="http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailInfo?ServiceKey="+Key+"&contentTypeId=25&contentId="+contentid+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&listYN=Y";
        Adapter2 = new Adapter2();
       recyclerView.setLayoutManager(layoutManager);
       recyclerView.setAdapter(Adapter2);

       new Thread(new Runnable() {
            @Override
            public void run() {
                parsing();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Adapter2.setItems(courselist);
                    }
                });
            }
        }).start();
    }




        public void parsing() {

            try {
                URL url = new URL(requesturl);
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
                                course = new course_info();
                                break;
                            }

                            if(startTag.equals("subdetailoverview")){
                                xpp.next();
                                Spanned spanned = Html.fromHtml(xpp.getText());
                                course.setOverview(spanned.toString());
                                Log.d("jmw93",xpp.getText());
                                break;
                            }
                            if(startTag.equals("subname")){
                                xpp.next();
                                course.setTitle(xpp.getText());
                                Log.d("jmw93",xpp.getText());
                                break;
                            }
                            if(startTag.equals("subnum")){

                                xpp.next();
                                course.setNum(xpp.getText());
                                Log.d("jmw93",xpp.getText());
                                break;
                            }
                            if(startTag.equals("subdetailimg")) {
                                xpp.next();
                                Log.d("jmw93",xpp.getText());
                                if(xpp.getText() !=null ){
                                    try {

                                        URL imgurl = new URL(xpp.getText());
                                        URLConnection conn = imgurl.openConnection();
                                        conn.connect();
                                        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                                        Bitmap bitmap = BitmapFactory.decodeStream(bis);
//                                        OOM 오류 발생시 아래코드 활성화
//                                        BitmapFactory.Options options =new BitmapFactory.Options();
//                                        options.inSampleSize=2;
//                                        Bitmap bitmap = BitmapFactory.decodeStream(bis,null,options);
                                        bis.close();
                                        course.setImg(bitmap);
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
                                courselist.add(course);

                            }
                            break;

                    }//switch문의 끝
                    eventType = xpp.next();
                }//for문 끝

            } //try문의 끝
            catch (Exception e){
                Log.e("jmw93",e.toString()+"파싱중오류");
            }

        }

}
