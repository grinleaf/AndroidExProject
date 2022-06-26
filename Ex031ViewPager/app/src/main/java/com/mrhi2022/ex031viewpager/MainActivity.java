package com.mrhi2022.ex031viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //뷰페이저가 보여줄 페이지의 이미지 리소스ID 데이터를 가진 리스트
    ArrayList<Integer> imgIds= new ArrayList<>();

    ViewPager2 pager;
    MyAdapter adapter;

    Button btnPrev, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //대량의 데이터를 추가(페이지별로 보여줄 이미지들)
        imgIds.add( R.drawable.bg_one01 );
        imgIds.add( R.drawable.bg_one02 );
        imgIds.add( R.drawable.bg_one03 );
        imgIds.add( R.drawable.bg_one04 );
        imgIds.add( R.drawable.bg_one05 );
        imgIds.add( R.drawable.bg_one06 );
        imgIds.add( R.drawable.bg_one07 );
        imgIds.add( R.drawable.bg_one08 );

        pager= findViewById(R.id.pager);
        adapter= new MyAdapter(this, imgIds);
        pager.setAdapter(adapter);

        //페이지 이동 버튼
        btnPrev= findViewById(R.id.btn_prev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //현재페이지의 1페이지 전
                int index= pager.getCurrentItem()-1;
                //특정페이지로 이동
                pager.setCurrentItem(index, true);
            }
        });

        btnNext= findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index= pager.getCurrentItem()+1;
                pager.setCurrentItem(index, false);
            }
        });

    }
}