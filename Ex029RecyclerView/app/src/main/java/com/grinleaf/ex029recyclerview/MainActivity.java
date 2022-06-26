package com.grinleaf.ex029recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //대량의 데이터
    ArrayList<Item> items= new ArrayList<>();
    MyAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //대량의 데이터 추가 : 실무에서는 서버나 DB 에서 가져오는 코드 작성되는 자리
        items.add(new Item("sam","Hello world"));
        items.add(new Item("kim","사과"));
        items.add(new Item("lee","오렌지"));
        items.add(new Item("park","포도"));
        items.add(new Item("song","파인애플"));

        recyclerView= findViewById(R.id.recycler);
        adapter= new MyAdapter(this, items);
        recyclerView.setAdapter(adapter);

        //아이템 클릭 시 반응하는 리스너는 recyclerView 로 설정 불가! --> ItemView 에 직접 클릭 리스너를 설정해야함
        //MyAdapter.java 문서에서 직접 코딩할 것...RecyclerView 의 단점..
    }
}