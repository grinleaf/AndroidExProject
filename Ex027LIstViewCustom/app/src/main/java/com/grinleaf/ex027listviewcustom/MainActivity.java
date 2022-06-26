package com.grinleaf.ex027listviewcustom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

        //대량의 데이터를 추가 - 원래 실무에서는 서버나 DB 에서 읽어옴
        items.add(new Item("전현무","대한민국",R.drawable.flag_korea));
        items.add(new Item("기욤패트리","캐나다",R.drawable.flag_canada));
        items.add(new Item("타일러","미국",R.drawable.flag_usa));
        items.add(new Item("알베르토" ,"이탈리아",R.drawable.flag_italy));
        items.add(new Item("타쿠야","일본",R.drawable.flag_japan));
        items.add(new Item("전현무","대한민국",R.drawable.flag_korea));
        items.add(new Item("기욤패트리","캐나다",R.drawable.flag_canada));
        items.add(new Item("타일러","미국",R.drawable.flag_usa));
        items.add(new Item("알베르토" ,"이탈리아",R.drawable.flag_italy));
        items.add(new Item("타쿠야","일본",R.drawable.flag_japan));

        listView= findViewById(R.id.listview);
        adapter= new MyAdapter(this, items);
        listView.setAdapter(adapter);

        //리스트뷰의 항목을 클릭했을 때 해당 항목의 name 데이터를 다이얼로그로 보여주기
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //세번째 파라미터 i : 클릭한 항목 position

                //해당 항목 Item 의 name 변수값 얻어오기
                String name= items.get(i).name;

                //AlertDialog 를 만들어주는 건축가(Builder) 객체 생성
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(name+"을 클릭했습니다.");
                builder.create().show();
            }
        });

    }
}