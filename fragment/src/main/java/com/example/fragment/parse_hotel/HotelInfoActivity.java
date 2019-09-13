package com.example.fragment.parse_hotel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.util.Linkify;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragment.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HotelInfoActivity extends AppCompatActivity {
    int contentid;
    int contenttypeid;
    ArrayList<Hotel_info> hotelinfolist = new ArrayList<>();
    Hotel_info hotel_info= new Hotel_info();
    TextView title;
    TextView homepage;
    TextView tel;
    ImageView image;
    TextView tvLinkfy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_info);
        title =findViewById(R.id.title);
        homepage=findViewById(R.id.homepage);
        tel = findViewById(R.id.tel);
        image=findViewById(R.id.image);
        tvLinkfy =findViewById(R.id.tvLinkify);

        Intent intent = getIntent();
        contentid= intent.getExtras().getInt("contentid");
        contenttypeid=intent.getExtras().getInt("contenttypeid");

        Log.d("hotelinfotest","아이디:"+contenttypeid+","+contentid);
        new Thread(new Runnable() {
            @Override
            public void run() {
                    parsing();
                Log.d("hotellisttest","아이템갯수:"+hotelinfolist.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       setting();
                       reservation();
                    }
                });
            }
        }).start();
    }



        public void setting(){
           title.setText(hotel_info.getTitle());
           tel.setText(hotel_info.getTel());
           homepage.setText(hotel_info.getHomepage());
           image.setImageBitmap(hotel_info.getFirstimage());
        }

        public void reservation(){
           String url=hotel_info.getHomepage();
           tvLinkfy.setText("예약바로가기");
            Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
                @Override
                public String transformUrl(Matcher match, String url) {
                    return "";
                }
            };
            Pattern pattern = Pattern.compile("예약바로가기");
            Linkify.addLinks(tvLinkfy,pattern,url,null,mTransform);

    }

         public void parsing() {
        String urlrequest="http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?" +
                "ServiceKey=RjzMYQORqJIq4l9YZkCCmV5mTIec%2BdJYC%2BUzK3c2Aogy4I2Y0tZnRI4292OO56Qqr%2FIMajYNHjo5M8Ayz4R05g%3D%3D&" +
                "contentTypeId="+contenttypeid+"&contentId="+contentid+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
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
                            hotel_info = new Hotel_info();
                            break;
                        }

                        if(startTag.equals("title")){
                            xpp.next();
                            hotel_info.setTitle(xpp.getText());
                            Log.d("hoteltest",xpp.getText());
                            break;
                        }
                        if(startTag.equals("tel")){
                            xpp.next();
                            hotel_info.setTel(xpp.getText());

                            break;
                        }
                        if(startTag.equals("mapx")){
                            xpp.next();
                            hotel_info.setMapx(xpp.getText());

                            break;
                        }
                        if(startTag.equals("mapy")){
                            xpp.next();
                            hotel_info.setMapy(xpp.getText());

                            break;
                        }

                        if(startTag.equals("homepage")){
                            xpp.next();
                            Spanned htmltext= Html.fromHtml(xpp.getText());
                            String targetString=htmltext.toString();
                            int startindex= targetString.indexOf("h");
                            String finaltext= targetString.substring(startindex);
                            hotel_info.setHomepage(finaltext);
                            Log.d("hoteltest",finaltext);
                            break;
                        }
                        if(startTag.equals("firstimage")){
                            xpp.next();
                            try {
                                URL imgurl = new URL(xpp.getText());
                                URLConnection conn = imgurl.openConnection();
                                conn.connect();
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize=2;
                                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                                Bitmap bitmap = BitmapFactory.decodeStream(bis,null,options);
                                bis.close();
                                hotel_info.setFirstimage(bitmap);

                            }catch (Exception e){
                                Log.d("jmw93","이미지로딩실패");
                            }
                            break;
                        }



                        break;

                    case XmlPullParser.END_TAG:
                        String endTag =xpp.getName();
                        if(endTag.equals("item")){
                            hotelinfolist.add(hotel_info);

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
