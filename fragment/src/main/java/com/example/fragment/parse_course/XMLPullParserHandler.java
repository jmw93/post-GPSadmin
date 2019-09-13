package com.example.fragment.parse_course;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.fragment.parse_Tour.Tour;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class XMLPullParserHandler {
     ArrayList<Course> courselist = new ArrayList<>();
    Course course;
    URL imgurl;

    public ArrayList<Course> getcourselist() {
        return courselist;

    }

    public ArrayList<Course> parsing() {

        String urlrequest ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/a" +
                "reaBasedList?ServiceKey=" +
                "RjzMYQORqJIq4l9YZkCCmV5mTIec%2BdJYC%2BUzK3c2Aogy4I2Y0tZnRI4292OO56Qqr%2FIMajYNHjo5M8Ayz4R05g%3D%3D&" +
                "contentTypeId=25&areaCode=1&sigunguCode=&cat1=C01&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=P&numOfRows=25&pageNo=1";
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
                            course = new Course();
                            break;
                        }

                        if(startTag.equals("title")){
                            xpp.next();
                            course.setTitle(xpp.getText());
                            break;
                        }
                        if(startTag.equals("contentid")){
                            xpp.next();
                            course.setContentid(Integer.parseInt(xpp.getText().toString()));
                            break;
                        }
                        if(startTag.equals("contenttypeid")){

                            xpp.next();
                            course.setContenttypeid(Integer.parseInt(xpp.getText().toString()));
                            break;
                        }
                        if(startTag.equals("firstimage")) {
                            xpp.next();
                            if(xpp.getText() !=null ){
                                try {

                                    imgurl = new URL(xpp.getText());
                                    URLConnection conn = imgurl.openConnection();
                                    conn.connect();
                                    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inSampleSize=2;
                                    Bitmap bitmap = BitmapFactory.decodeStream(bis,null,options);
                                    bis.close();
                                    course.setImage(bitmap);
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

        Log.d("sae1013",courselist.toString());
        return courselist;
    }
}