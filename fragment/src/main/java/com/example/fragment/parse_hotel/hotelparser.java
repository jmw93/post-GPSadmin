package com.example.fragment.parse_hotel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.text.Spanned;
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

public class hotelparser {
    ArrayList<Hotel_info> hotelinfolist = new ArrayList<>();
    Hotel_info hotel_info;
    int contentid;
    int contenttypeid;
    //생성자 . 액티비티로 컨텐트아이디하고타입 받음 (생성시)

    public hotelparser(int contentid, int contenttypeid) {
            this.contentid = contentid;
            this.contenttypeid=contenttypeid;
    }

    public ArrayList<Hotel_info> parsing() {
        String urlrequest="http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey=RjzMYQORqJIq4l9YZkCCmV5mTIec%2BdJYC%2BUzK3c2Aogy4I2Y0tZnRI4292OO56Qqr%2FIMajYNHjo5M8Ayz4R05g%3D%3D&contentTypeId=32&contentId=142729&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
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
                            break;
                        }
                        if(startTag.equals("tel")){
                            xpp.next();
                            hotel_info.setTitle(xpp.getText());
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
                            hotel_info.setHomepage(htmltext.toString());
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
                                Log.d("jmw93","이미지전환 끝");
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

        Log.d("hoteltest","갯수:"+hotelinfolist.size());
        return hotelinfolist;
    }
}
