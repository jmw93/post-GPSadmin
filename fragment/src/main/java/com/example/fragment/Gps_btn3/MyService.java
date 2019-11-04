package com.example.fragment.Gps_btn3;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import com.example.fragment.Gps_btn3.GMailSender;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.net.URL;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class MyService extends Service {
    LocationRequest request;
    private FusedLocationProviderClient mFusedLocationClient;
    TextView textView;
    private LocationCallback mLocationCallback;
    String emailAddr;
    String emailPassword;
    String recipient;
    int time;
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mLocationCallback != null && mFusedLocationClient!= null) {
            stopLocationUpdates(); //중복실행 코드수정하기
        }
        String settingtime= intent.getStringExtra("settingtime").replaceAll("[^0-9]", "");
        time= Integer.parseInt(settingtime)*60000;
        emailAddr = intent.getStringExtra("emailAddr");
        emailPassword = intent.getStringExtra("emailPassword");
        recipient = intent.getStringExtra("recipient");
        Log.d("jmw93","이메일주소:"+emailAddr);
        Log.d("jmw93","비밀번호:"+emailPassword);
        Log.d("jmw93","상대방주소"+recipient);

        request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(time);
        request.setFastestInterval(50000);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    for (final Location location : locationResult.getLocations()) {
                        final double Latitude = location.getLatitude();
                        final double Longitude = location.getLongitude();

                        try {
                            String requestUrl ="https://www.google.com/search?q="+Latitude+"%2C"+Longitude+"&rlz=1C1QJDB_enKR784KR784&oq="+Latitude+"%2C"+Longitude+"&aqs=chrome..69i57.2903j1j4&sourceid=chrome&ie=UTF-8";
                            URL locationurl=new URL(requestUrl);
                            Log.d("jmw93","위도"+Latitude+"경도"+Longitude);
                                    GMailSender gMailSender = new GMailSender(emailAddr,emailPassword);
                                    Log.d("jmw93","인증메일코드"+gMailSender.getEmailCode());
                                    //GMailSender.sendMail(제목, 본문내용, 받는사람);

                                    gMailSender.sendMail("앱에서 발송:나의위치","아래 URL을 클릭하면 지도로 이동합니다. \n" +
                                            "위도:"+Latitude+",경도:"+Longitude+"\n"+locationurl,recipient);


                        }
                                catch (SendFailedException e) {

                                    e.printStackTrace();

                                } catch (MessagingException e) {

                                    e.printStackTrace();
                                }
                        catch (Exception e) {

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

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("jmw93","메일발송을 중지합니다");
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
