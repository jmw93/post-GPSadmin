package com.example.fragment.parse_hotel;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragment.MainActivity;
import com.example.fragment.R;
import com.example.fragment.parse_Tour.Tour;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class hotel_fragment extends Fragment {
    MainActivity mainActivity;
    ArrayList<Hotel> hotellist = new ArrayList<>(); //이부분 때문에 2시간을 허비함... arrayList는 꼭 new 로 생성해주어야함.


    Activity activity;
    Hotel hotel;
    URL imgurl;
    Adapter adapter;
    LocationRequest request;
    public LocationCallback mLocationCallback;
    Double mapX;
    Double mapY;

 FusedLocationProviderClient mFusedLocationClient;
    public hotel_fragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("민우","onAttach()");
        mainActivity = (MainActivity)getActivity();
        if(context instanceof Activity){
            activity = (Activity)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("민우","onCreateView()");
        request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(100000);
        request.setFastestInterval(100000);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        ViewGroup view= (ViewGroup)inflater.inflate(R.layout.fragment_hotel_fragment, container, false);
        RecyclerView recyclerView =view.findViewById(R.id.recyclerView);


        mLocationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if(locationResult !=null){
                    for(final Location location : locationResult.getLocations()){
                        final double Latitude = location.getLatitude();
                        final double Longitude = location.getLongitude();
                        try{

                                    mapY = Latitude;
                                    mapX = Longitude;
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            parsing();
                                            activity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    adapter.setItems(hotellist);
                                                }
                                            });
                                        }
                                    }).start();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        mFusedLocationClient.requestLocationUpdates(
                request,
                mLocationCallback,
                null);

         adapter = new Adapter(new Adapter.onClickListener() {
            @Override
            public void onclicked(Hotel model) {
                int contentid =model.getContentid();
                int contenttypeid =model.getContenttype();
                mainActivity.sendcontenttypeid_hotel(contentid,contenttypeid);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    return view;
    }
    public void parsing() {

        String urlrequest ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList?ServiceKey=RjzMYQORqJIq4l9YZkCCmV5mTIec%2BdJYC%2BUzK3c2Aogy4I2Y0tZnRI4292OO56Qqr%2FIMajYNHjo5M8Ayz4R05g%3D%3D&contentTypeId=32&" +
                "mapX="+mapX+"&mapY="+mapY +
                "&radius=2000&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=P&numOfRows=30&pageNo=1";
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
                            hotel = new Hotel();
                            break;
                        }

                        if(startTag.equals("addr1")){
                            xpp.next();
                            hotel.setAddr(xpp.getText());
                            break;
                        }
                        if(startTag.equals("title")){
                            xpp.next();
                            Log.d("hoteltest",xpp.getText());
                            hotel.setTitle(xpp.getText());
                            break;
                        }
                        if(startTag.equals("contentid")){
                            xpp.next();
                            hotel.setContentid(Integer.parseInt(xpp.getText().toString()));
                            break;
                        }
                        if(startTag.equals("contenttypeid")){
                            xpp.next();
                            hotel.setContenttype(Integer.parseInt(xpp.getText().toString()));
                            break;
                        }
                        if(startTag.equals("firstimage2")){
                            xpp.next();
                            try {
                                imgurl = new URL(xpp.getText());
                                Log.d("hotelimage","주소"+imgurl);
                                URLConnection conn = imgurl.openConnection();
                                conn.connect();
                                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                                Bitmap bitmap = BitmapFactory.decodeStream(bis); //비트맵변환
                                bis.close();
                                int width=bitmap.getWidth();
                                int height=bitmap.getHeight();
                                Log.d("bitmap","가로세로:"+width+height);
                                if(width>400 || height>400){
                                    width= 355;
                                    height=230;
                                    bitmap=Bitmap.createScaledBitmap(bitmap,width,height,true);
                                    int new_width=bitmap.getWidth();
                                    int new_height=bitmap.getHeight();
                                    Log.d("bitmap","새로운 가로세로"+new_width+new_height);
                                }
                             hotel.setImage(bitmap);

                            }catch (Exception e){
                                Log.d("jmw93","이미지로딩실패");
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

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("민우","onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("민우","onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        Log.d("민우","onPause()");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("민우","onDestroyView()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("민우","onDetach()");
    }
}
