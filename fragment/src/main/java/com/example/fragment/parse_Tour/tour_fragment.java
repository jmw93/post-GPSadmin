package com.example.fragment.parse_Tour;

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

import com.example.fragment.MainActivity;
import com.example.fragment.R;
import com.example.fragment.parse_Tour.Adapter;
import com.example.fragment.parse_Tour.Tour;
import com.example.fragment.parse_Tour.XMLPullParserHandler;

import java.util.ArrayList;

public class tour_fragment extends Fragment {

    private Activity activity;
    MainActivity mainActivity;
    ArrayList<Tour> tourlist;

    Adapter adapter;
    RecyclerView recyclerView;
    XMLPullParserHandler xmlPullParserHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            mainActivity = (MainActivity)getActivity();
            activity =(Activity)context;
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.tourfragment, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        xmlPullParserHandler = new XMLPullParserHandler();

        adapter = new Adapter(new Adapter.OnTourClickListener() {
            @Override
            public void onTourClicked(Tour model) {


                int contentid = model.getContentid();
                int contenttypeid = model.getContenttypeid();
                mainActivity.sendcontenttypeid_tour(contentid,contenttypeid);

            }
        });
        tourlist = new ArrayList<Tour>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

            new Thread(new Runnable() {
            @Override
            public void run() {
                tourlist = xmlPullParserHandler.parsing();
                Log.d("jmw93", String.valueOf(tourlist.size()));

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setItems(tourlist);

                        recyclerView.setAdapter(adapter);

                    }

                });
            }
        }).start();


        return view;
    }
}
