package com.grinleaf.ex028listviewholder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //대량의 데이터
    ArrayList<String> items= new ArrayList<>();
    ListView listView;
    MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //대량의 데이터 추가 : 실무에서는 서버나 DB에서 가져오기
        items.add(new String("aaa"));
        items.add(new String("bbb"));
        items.add(new String("ccc"));
        items.add(new String("ddd"));

        //어댑터뷰 사용할 때 항상 있을 세 줄!
        listView= findViewById(R.id.listview);
        adapter= new MyAdapter(this, items);
        listView.setAdapter(adapter);

    }
}