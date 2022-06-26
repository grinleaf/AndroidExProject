package com.grinleaf.ex026gridview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayAdapter adapter;

    //대량의 데이터 리스트
    ArrayList<String> datas= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //대량의 데이터 추가 - 원래 실무에서는 서버나 DB 에서 데이터를 읽어온다
        datas.add( new String("aaa"));
        datas.add( new String("bbb"));
        datas.add( new String("ccc"));
        datas.add( new String("ddd"));
        datas.add( new String("eee"));
        datas.add("fff");       //자동 new String();
        datas.add("ggg");

        gridView= findViewById(R.id.gridview);
        adapter= new ArrayAdapter(this,R.layout.grindview_item,datas);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, datas.get(i),Toast.LENGTH_SHORT).show();
            }
        });

    }
}