package com.example.fragment.parse_course;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fragment.MainActivity;
import com.example.fragment.R;
import com.example.fragment.parse_Tour.Adapter;
import com.example.fragment.parse_Tour.Tour;
import com.example.fragment.parse_Tour.XMLPullParserHandler;

import java.util.ArrayList;

public class course_fragment extends Fragment {
    private Context mContext;
    private Activity activity;
    MainActivity mainActivity;
    ArrayList<Course> courselist;


    com.example.fragment.parse_course.Adapter adapter;
    RecyclerView recyclerView;
    com.example.fragment.parse_course.XMLPullParserHandler xmlPullParserHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("테스트","onAttach실행");
        mContext= context;
        if(context instanceof Activity){
            activity =(Activity)context;
            mainActivity = (MainActivity)getActivity();
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.course_fragment, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        Log.d("테스트","onCreateView실행");
        xmlPullParserHandler = new com.example.fragment.parse_course.XMLPullParserHandler();
        adapter = new com.example.fragment.parse_course.Adapter(new com.example.fragment.parse_course.Adapter.OncourseClickListener() {
                    @Override
            public void onCourseClicked(Course model) {
                Toast.makeText(mainActivity, "아이템클릭", Toast.LENGTH_SHORT).show();
                int contentid =model.getContentid(); //contentid만쓰임.
                int contenttypeid =model.getContenttypeid();
                mainActivity.sendcontenttypeid_course(contentid,contenttypeid);



            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

            new Thread(new Runnable() {
            @Override
            public void run() {
                courselist = xmlPullParserHandler.parsing();
                Log.d("jmw93", "courselist의 갯수:"+String.valueOf(courselist.size()));

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setItems(courselist);

                        recyclerView.setAdapter(adapter);

                    }

                });
            }
        }).start();


        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d("test","onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("test","onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("test","onPause()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("test","onDestroyView()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("test","onDetach()");
    }
}
