package com.example.fragment.parse_Tour;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment.MainActivity;
import com.example.fragment.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Tour_informActivity extends FragmentActivity {
    private GoogleMap mMap;
    FrameLayout frameLayout;
    Double Lat;
    Double Lng;
    Imgdata imgdata;
    Imgdata loop_imgdata;
    Infodata infodata;
    URL originimgurl;
    URL smallimgurl;
    Button button;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    TextView textView_chkbabycarriage;
    TextView textView_chkpet;
    TextView textView_overView;
    TextView textView_infocenter;
    TextView textView_parking;
    LocationRequest request;
    FusedLocationProviderClient mFusedLocationClient;
    LocationCallback mLocationCallback;
    double current_latitude;
    double current_longitude;
    int contentid;
    int contenttypeid;
    SupportMapFragment mapFragment;
    String ServiceKey ="RjzMYQORqJIq4l9YZkCCmV5mTIec%2BdJYC%2BUzK3c2Aogy4I2Y0tZnRI4292OO56Qqr%2FIMajYNHjo5M8Ayz4R05g%3D%3D";
    ArrayList<Imgdata> imgdatalist = new ArrayList<Imgdata>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);
        Button button_navi = findViewById(R.id.button_navi);
        Button button_map = findViewById(R.id.button_map);
        frameLayout =findViewById(R.id.framelayout);
        frameLayout.setVisibility(View.GONE);
        mapFragment =(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

       request= new LocationRequest();
       request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
       request.setInterval(1000);
       request.setFastestInterval(0);
       mFusedLocationClient= LocationServices.getFusedLocationProviderClient(this);
       mLocationCallback = new LocationCallback(){
           @Override
           public void onLocationResult(LocationResult locationResult) {
               if(locationResult != null){
                   for(final Location location : locationResult.getLocations()){
                       current_latitude=location.getLatitude();
                       current_longitude=location.getLongitude();
                   }

               }
           }
       };
        mFusedLocationClient.requestLocationUpdates(
                request,
                mLocationCallback,
                null);

        //지도버튼 리스너
        button_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        frameLayout.setVisibility(View.VISIBLE);
                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                mMap = googleMap;
                                LatLng mylocation = new LatLng(Lat,Lng);
                                mMap.addMarker(new MarkerOptions().position(mylocation).title("Me"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));

                            }
                }); // 얘를 버튼클릭시, 붙여주어야함.
            }
        });
        //네비버튼 리스너
        button_navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current_latitude !=0 && current_longitude != 0) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("https://maps.google.com/maps?saddr=" + current_latitude + "," + current_longitude + "&daddr=" + Lat + "," + Lng));

                    startActivity(intent);
                }else{
                    Toast.makeText(Tour_informActivity.this, "Loading...try again", Toast.LENGTH_SHORT).show();
                }
            }

        });

        textView_chkbabycarriage =findViewById(R.id.textView_chkbabycarriage);
        textView_chkpet=findViewById(R.id.textView_chkpet);
        textView_overView=findViewById(R.id.textView_overView);
        textView_infocenter=findViewById(R.id.textView_infocenter);
        textView_parking=findViewById(R.id.textView_parking);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        Intent intent = getIntent();
        contentid= intent.getExtras().getInt("contentid");
        contenttypeid=intent.getExtras().getInt("contenttypeid");

        imgdata = new Imgdata();
        infodata = new Infodata();
//        IntentData data = (IntentData) intent.getSerializableExtra("targetData");
//        contentid =data.getContentid();
//        contenttypeid=data.getContenttypeid();
//        Log.d("minwoo","contenttypeid: "+data.getContenttypeid());
//        Log.d("minwoo","contenttypeid: "+data.getContentid());

        new Thread(new Runnable() {
            @Override
            public void run() {
                imgdatalist = parsing();
                parsing2();  //parsing2 , parsing3 모두 infodata 객체에 결과저장.
                parsing3();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(imgdatalist.size()>1) {
                            image1.setImageBitmap(imgdatalist.get(0).getOriginimgurl());
                            image2.setImageBitmap(imgdatalist.get(1).getOriginimgurl());
                            image3.setImageBitmap(imgdatalist.get(2).getOriginimgurl());
                            image4.setImageBitmap(imgdatalist.get(3).getOriginimgurl());

                            initinfo();
                            Log.d("locationtest","위치:"+Lat+","+Lng);
                        } else{
                            Toast.makeText(Tour_informActivity.this,"표시할 데이터가 없습니다",Toast.LENGTH_SHORT).show();
                        }
                        Log.d("parsing3",infodata.toString());




                    }
                });
            }
        }).start();

    }
    public void initinfo(){

        textView_chkbabycarriage.setText(infodata.getChkbabycarriage());
        textView_infocenter.setText(infodata.getInfocenter());
        textView_chkpet.setText(infodata.getChkpet());
        textView_overView.setText(infodata.getOverview());
        textView_parking.setText(infodata.getParking());
        Lng= Double.parseDouble(infodata.getMapx());
        Lat= Double.parseDouble(infodata.getMapy());
}
    //parsing() 은 맨위 이미지 파싱들을 위한것
    public ArrayList<Imgdata> parsing() {

        String urlrequest ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage?ServiceKey=RjzMYQORqJIq4l9YZkCCmV5mTIec%2BdJYC%2BUzK3c2Aogy4I2Y0tZnRI4292OO56Qqr%2FIMajYNHjo5M8Ayz4R05g%3D%3D&contentTypeId="+contenttypeid+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&contentId="+contentid+"&imageYN=Y";
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
                        Log.d("minwoo2",startTag);

                        if(startTag.equals("item")){
                            imgdata = new Imgdata();
                            break;
                        }

                        if(startTag.equals("originimgurl")){
                            xpp.next();
                            try{
                                originimgurl = new URL(xpp.getText());
                                URLConnection conn = originimgurl.openConnection();
                                conn.connect();
                                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                                Bitmap bitmap = BitmapFactory.decodeStream(bis);
                                bis.close();
                                imgdata.setOriginimgurl(bitmap);
                            }catch(Exception e){
                                Log.d("minwoo2",e.toString());

                            }

                            break;
                        }
                        if(startTag.equals("smallimageurl")){
                            xpp.next();
                            try{
                                smallimgurl = new URL(xpp.getText());
                                URLConnection conn = smallimgurl.openConnection();
                                conn.connect();
                                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize=2;
                                Bitmap bitmap = BitmapFactory.decodeStream(bis,null,options);
                                bis.close();
                                int width =bitmap.getWidth();
                                int height= bitmap.getHeight();
                                Log.d("imgtest","이미지사이즈:"+width+height);
                                imgdata.setSmallimageurl(bitmap);
                            }catch(Exception e){
                                Log.d("minwoo2",e.toString());

                            }
                            break;
                        }


                        break;

                    case XmlPullParser.END_TAG:
                        String endTag =xpp.getName();
                        if(endTag.equals("item")){
                            imgdatalist.add(imgdata);

                        }
                        break;

                }//switch문의 끝
                eventType = xpp.next();
            }//for문 끝

        } //try문의 끝
        catch (Exception e){
            Log.e("jmw93",e.toString()+"파싱중오류");
        }

        Log.d("jmw93","파싱성공");
        return imgdatalist;
    }

    /*parsing2파싱 ( 공통정보) mapx, mapy는 왜 파싱한거야?*/
    public  void parsing2() {

        String urlrequest ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?" +
                "ServiceKey=" + ServiceKey+
                "&contentTypeId="+contenttypeid+"&contentId="+contentid+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=N&areacode=N&catcodeYN=N&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
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
                        Log.d("parsing2",startTag);

                        if(startTag.equals("item")){

                            break;
                        }

                        if(startTag.equals("mapx")){
                            xpp.next();
                            infodata.setMapx(xpp.getText());

                            break;
                        }
                        if(startTag.equals("mapy")){
                            xpp.next();
                            infodata.setMapy(xpp.getText());
                            break;
                        }

                        if(startTag.equals("overview")){
                            xpp.next();
                            Spanned moverview = Html.fromHtml(xpp.getText());
                            infodata.setOverview(moverview.toString());
                            break;
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        String endTag =xpp.getName();
                        if(endTag.equals("item")){


                        }
                        break;

                }//switch문의 끝
                eventType = xpp.next();
            }//for문 끝

        } //try문의 끝
        catch (Exception e){
            Log.e("jmw93",e.toString()+"파싱중오류");
        }

        Log.d("jmw93","parsing2성공");

    }

    //parsing3 유모차/운영시간 정보 파싱
    public  void parsing3() {

        String urlrequest ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?" +
                "ServiceKey="+ServiceKey +
                "&contentTypeId="+contenttypeid+"&contentId="+contentid+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";
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
                        Log.d("parsing3",startTag);

                        if(startTag.equals("item")){

                            break;
                        }

                        if(startTag.equals("chkbabycarriage")){
                            xpp.next();
                            infodata.setChkbabycarriage(xpp.getText());

                            break;
                        }
                        if(startTag.equals("chkpet")){
                            xpp.next();
                            infodata.setChkpet(xpp.getText());
                            break;
                        }

                        if(startTag.equals("infocenter")){
                            xpp.next();
                            infodata.setInfocenter(xpp.getText());
                            break;
                        }

                        if(startTag.equals("parking")){
                            xpp.next();
                            infodata.setParking(xpp.getText());
                            break;
                        }
                        if(startTag.equals("restdate")){
                            xpp.next();
                            infodata.setRestdate(xpp.getText());
                            break;
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        String endTag =xpp.getName();
                        if(endTag.equals("item")){


                        }
                        break;

                }//switch문의 끝
                eventType = xpp.next();
            }//for문 끝

        } //try문의 끝
        catch (Exception e){
            Log.e("jmw93",e.toString()+"파싱중오류");
        }

        Log.d("jmw93","파싱성공");

    }



    @Override
    protected void onPause() {
        super.onPause();
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }
}
