package com.grinleaf.tp06adapterviewex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> items= new ArrayList<>();
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyAdapter adapter;

        items.add(new Item("전현무", "한국", R.drawable.flag_korea));
        items.add(new Item("기욤패트리", "캐나다", R.drawable.flag_canada));
        items.add(new Item("타일러", "미국", R.drawable.flag_usa));
        items.add(new Item("알베르토", "이탈리아", R.drawable.flag_italy));
        items.add(new Item("타쿠야", "일본", R.drawable.flag_japan));

        listView= findViewById(R.id.listview);
        adapter= new MyAdapter(this,items);
        listView.setAdapter(adapter);


    }
}