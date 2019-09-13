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

    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mLocationCallback != null && mFusedLocationClient!= null) {
            stopLocationUpdates();
        }
        emailAddr = intent.getStringExtra("emailAddr");
        emailPassword = intent.getStringExtra("emailPassword");
        recipient = intent.getStringExtra("recipient");
        Log.d("jmw93","이메일주소:"+emailAddr);
        Log.d("jmw93","비밀번호:"+emailPassword);
        Log.d("jmw93","상대방주소"+recipient);

        request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(10000);
        request.setFastestInterval(5000);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    for (final Location location : locationResult.getLocations()) {
                        final double Latitude = location.getLatitude();
                        final double Longitude = location.getLongitude();


                        try {
                            Log.d("jmw93","위도"+Latitude+"경도"+Longitude);
                                    GMailSender gMailSender = new GMailSender(emailAddr, emailPassword);
                                    Log.d("jmw93","인증메일코드"+gMailSender.getEmailCode());
                                    //GMailSender.sendMail(제목, 본문내용, 받는사람);

                                    gMailSender.sendMail("앱에서 발송:나의위치","다음 위도,경도값을 복사한후, google.com에 붙여넣기하세요\n" +
                                            Latitude+","+Longitude+"\n"+"혹은 아래 URL을 복사붙여넣기 하세요\n"
                                            +"서비스준비중..",recipient);


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
