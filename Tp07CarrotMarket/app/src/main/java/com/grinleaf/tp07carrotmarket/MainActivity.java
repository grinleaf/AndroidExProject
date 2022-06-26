package com.grinleaf.tp07carrotmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Trade> trades= new ArrayList<>();
    MyAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trades.add(new Trade(R.drawable.bazzi,"침대 협탁 팔아요", "역삼1동-방금", "35,000원",3,6));
        trades.add(new Trade(R.drawable.marid,"[동네빵네] 사거리 빵집 오픈", "서초동-지역광고", "6,500원",9,42));
        trades.add(new Trade(R.drawable.dao,"곰돌이 채칼세트", "역삼2동-방금", "9,000원",1,5));
        trades.add(new Trade(R.drawable.ethi,"랄랄랄라", "역삼2동-2일전", "12,000원",8,0));
        trades.add(new Trade(R.drawable.dizni,"어려워용", "서초동-1일 전", "350,000원",2,10));

        recyclerView= findViewById(R.id.recycler);
        adapter= new MyAdapter(this,trades);
        recyclerView.setAdapter(adapter);
    }
}