package com.example.fragment.main_View_btn1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.fragment.R;

public class webViewActivity extends AppCompatActivity {
        WebView mwebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        String Url = intent.getExtras().getString("URL");
        mwebView=findViewById(R.id.webView);
        mwebView.getSettings().setJavaScriptEnabled(true);
        mwebView.loadUrl(Url);
        mwebView.setWebChromeClient(new WebChromeClient());

        mwebView.setWebViewClient(new WebViewClientClass());



    }
        private class WebViewClientClass extends WebViewClient {//페이지 이동
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mwebView.loadUrl("javascript:(function() { " +
                        "document.getElementById('seoul-common-gnb').style.display='none';" + //상단 메뉴바
                        "document.getElementsByClassName('wrapper-inner')[0].style.display='none'; " + //VISITSEOULNET 배너
                        "document.getElementsByClassName('sub-detail-visual-wrap default')[0].style.display='none';"+ // 배너사진
                        "document.getElementsByClassName('livere-wrap')[0].style.display='none';"+    //안내 삭제
                        "document.getElementsByTagName('footer')[0].style.display='none';"+             //footer삭제
                        "document.getElementsByClassName('service-menu-wrap')[0].style.display='none';"+ //최하단 서비스메뉴삭제
                        "document.getElementsByClassName('right-content')[0].style.display='none';"+ //상세정보 (제작일/수정일 숨기기)
                        "})()");
            }
        }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//뒤로가기 버튼 이벤트
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mwebView.canGoBack()) {//웹뷰에서 뒤로가기 버튼을 누르면 뒤로가짐
            mwebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
