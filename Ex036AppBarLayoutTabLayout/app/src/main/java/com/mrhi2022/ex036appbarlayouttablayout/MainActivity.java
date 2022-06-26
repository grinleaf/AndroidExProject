package com.mrhi2022.ex036appbarlayouttablayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;

    ViewPager2 pager;
    MyAdapter adapter;

    String[] tabTitle= new String[]{"Home","Shop","App"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //툴바를 제목줄로 대체
        MaterialToolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout= findViewById(R.id.layout_tab);

        //이렇게 직접 탭을 만들고 리스너를 붙이는 작업은 아주 예전 방식
        //탭버튼(Tab객체) 생성 및 붙이기
//        TabLayout.Tab tab= tabLayout.newTab();
//        tab.setId(0);
//        tab.setText("TAB1");
//        tabLayout.addTab(tab);
//
//        TabLayout.Tab tab2= tabLayout.newTab().setText("TAB2").setId(1);
//        tabLayout.addTab(tab2);
//
//        tabLayout.addTab( tabLayout.newTab().setText("TAB3").setId(2) );
//        tabLayout.addTab( tabLayout.newTab().setText("TAB3") );
//        tabLayout.addTab( tabLayout.newTab().setText("TAB3") );
//        tabLayout.addTab( tabLayout.newTab().setText("TAB3") );
//        tabLayout.addTab( tabLayout.newTab().setText("TAB3") );
//        tabLayout.addTab( tabLayout.newTab().setText("TAB3") );
//        tabLayout.addTab( tabLayout.newTab().setText("TAB3") );
//        tabLayout.addTab( tabLayout.newTab().setText("TAB3") );
//        tabLayout.addTab( tabLayout.newTab().setText("TAB3") );
//        tabLayout.addTab( tabLayout.newTab().setText("TAB3") );

        pager= findViewById(R.id.pager);
        adapter= new MyAdapter(this);
        pager.setAdapter(adapter);

        //*** 뷰페이저와 TabLayout 을 연동하기 ****
        // 연동을 하면 자동으로 뷰페이저의 페이지 개수만큼 Tab버튼이 만들어짐.
        // ViewPager와 TabLayout와 중재자 역할의 객체 생성
        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //페이지 개수만큼 이 메소드가 발동함.
                //첫번째 파라미터 : 자동으로 만들어질 Tab객체
                //두번째 파라미터 : 만들번째 위치 position
                //tab.setText("TAB" + (position+1) );
                tab.setText( tabTitle[position] );

            }
        }).attach(); // .attach() 를 잊지말고 호출!!


//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//                int id= tab.getId();
//                pager.setCurrentItem(id, true);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//
//                TabLayout.Tab tab= tabLayout.getTabAt(position);
//                tabLayout.selectTab(tab);
//            }
//        });


    }

    //onCreate()메소드가 실행된 후 OptionMenu를 만들기 위해 자동으로 발동하는 콜백메소드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return super.onCreateOptionsMenu(menu);
    }
}