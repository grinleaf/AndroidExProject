package com.grinleaf.ex031viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Viewpager 가 보여주는 페이지의 이미지 리소스 ID 데이터를 가진 리스트
    ArrayList<Integer> imgIds= new ArrayList<>();

    //
    ViewPager2 pager;
    MyAdapter adapter;

    //페이지 이동 버튼 참조변수
    Button btnPrev, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //대량의 데이터 : 페이지별로 보여줄 이미지들 직접 추가
        imgIds.add(R.drawable.img01);
        imgIds.add(R.drawable.img02);
        imgIds.add(R.drawable.img03);
        imgIds.add(R.drawable.img04);
        imgIds.add(R.drawable.img05);
        imgIds.add(R.drawable.img06);
        imgIds.add(R.drawable.img07);
        imgIds.add(R.drawable.img08);

        //
        pager.findViewById(R.id.pager);
        adapter= new MyAdapter(this,imgIds);
        pager.setAdapter(adapter);

        //페이지 이동 버튼 만들기
        btnPrev= findViewById(R.id.btn_prev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //현재 페이지의 1페이지 전
                int index= pager.getCurrentItem()-1;
                //특정 페이지로 이동하기 : 참고로 첫페이지에서 -1할 경우 첫페이지를 그대로 가짐. 마지막페이지에서 +1 했을 경우에도 마찬가지
                pager.setCurrentItem(index,true);       //파라미터 : (요소번호, 부드럽게움직이기-true/뚝뚝끊기면서변경-false)
            }
        });
        btnNext= findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //현재 페이지의 1페이지 후
                int index= pager.getCurrentItem()+1;
                pager.setCurrentItem(index,false);
            }
        });

    }
}