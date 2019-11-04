package com.example.fragment.main_View_btn1;

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

import com.example.fragment.R;
import com.example.fragment.onwebListener;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    public onwebListener monwebListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            monwebListener = (onwebListener)getActivity();
        }catch(Exception e){
            Log.e("jmw93","onAttach오류");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.mainfragment,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        Adapter2 cardAdapter = new Adapter2(new Adapter2.objectOnClickListener() {
            @Override
            public void onClicked(object model) {
                Toast.makeText(getContext(), "아이템선택됨", Toast.LENGTH_SHORT).show();
                String URL= model.getURL();
                monwebListener.sendwebView(URL);


            }
        });
        ArrayList<object> objects = new ArrayList<>();
        object object1 = new object("나만의 서울 사진 명당: I SEOUL U"
                ,"서울 곳곳에 설치된 I SEOUL U를 찾아 떠나자");
        object1.setImgpath(R.drawable.iseoul4);
        object1.setURL("http://korean.visitseoul.net/tours/%EB%82%98%EB%A7%8C%EC%9D%98-%EC%84%9C%EC%9A%B8-%EC%82%AC%EC%A7%84-%EB%AA%85%EB%8B%B9-I-SEOUL-U_/29486?curPage=1");

        object object2 = new object("나.혼.산 성훈의 추천","<나 혼자 산다> 성훈과 함께하는 서울여행");
        object2.setImgpath(R.drawable.withseonghoon);
        object2.setURL("http://korean.visitseoul.net/tours/%EB%82%98-%ED%98%BC%EC%9E%90-%EC%82%B0%EB%8B%A4-%EC%84%B1%ED%9B%88%EA%B3%BC-%ED%95%A8%EA%BB%98%ED%95%98%EB%8A%94-%EC%84%9C%EC%9A%B8-%EC%97%AC%ED%96%89_/30453?curPage=3");

        object object3 = new object("기억의 보관소,돈의문박물관 마을"
                ,"선선한 날씨에 걷기 좋은 도심 속 공간");
        object3.setImgpath(R.drawable.img_museum);
        object3.setURL("http://korean.visitseoul.net/tours/%EA%B8%B0%EC%96%B5%EC%9D%98-%EB%B3%B4%EA%B4%80%EC%86%8C-%EB%8F%88%EC%9D%98%EB%AC%B8%EB%B0%95%EB%AC%BC%EA%B4%80%EB%A7%88%EC%9D%84_/29410?curPage=1");

        object object4 = new object("서울의 밤: 잠들지 않는도시"
                ,"서울 밤놀이를 떠나자");
        object4.setImgpath(R.drawable.img_1932);

        object object5 = new object("혼자 걷고싶은 옛 길","서울의 회로애락길");
        object5.setImgpath(R.drawable.walking);
        object5.setURL(" http://korean.visitseoul.net/tours/%EB%A7%88%EC%9D%8C%EC%9D%B4-%EB%82%B4%ED%82%A4%EB%8A%94-%EB%8C%80%EB%A1%9C-%EC%A6%90%EA%B8%B0%EB%8A%94-%EC%84%9C%EC%9A%B8%EC%9D%98-%ED%9D%AC%EB%A1%9C%EC%95%A0%EB%9D%BD-%EA%B8%B8_/28528?curPage=6");


        objects.add(object1);
        objects.add(object2);
        objects.add(object3);
        objects.add(object4);
        objects.add(object5);

        Log.d("jmw93","object객체에 이미지주소값:"+objects.get(0).getImgpath());
        cardAdapter.setItems(objects);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cardAdapter);



        return view;
    }
}
