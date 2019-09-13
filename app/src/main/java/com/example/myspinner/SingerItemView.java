package com.example.myspinner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingerItemView extends LinearLayout {
    TextView textview1;
    TextView textview2;

    public SingerItemView(Context context) {
        super(context);
        init(context);
    }

    public SingerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item, this ,true);
        textview1 =(TextView)findViewById(R.id.textView1);
        textview2 =(TextView)findViewById(R.id.textView2);
        }

        public void setName(String name){
        textview1.setText(name);
        }

        public void setMobile(String mobile){
        textview2.setText(mobile);
        }

}
