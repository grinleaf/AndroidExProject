package com.mrhi2022.ex037drawernavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    NavigationView nav;

    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //툴바를 제목줄(ActionBar)로 대체하기
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout= findViewById(R.id.layout_drawer);

        //제목줄의 왼쪽에 붙어있는 삼선아이콘(햄버거아이콘) 메뉴를 통해 Drawer를 열고 닫기
        //삼선아이콘 버튼 만들기
        drawerToggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open, R.string.drawer_close);
        //삼선모양이 보이도록 토글버튼의 동기 맞추기..
        drawerToggle.syncState();
        //삼선아이콘모양과 화살표아이콘 모양이 자동으로 변환되도록
        drawerLayout.addDrawerListener(drawerToggle);

        nav= findViewById(R.id.nav);
        //네비게이션뷰의 아이템이 선택되었을때 반응하는 리스너
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch ( item.getItemId() ){
                    case R.id.menu_aa:
                        Toast.makeText(MainActivity.this, "aa", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.menu_bb:
                        Toast.makeText(MainActivity.this, "bb", Toast.LENGTH_SHORT).show();
                        break;
                }

                //Drawer 뷰를 닫기..
                drawerLayout.closeDrawer(nav,true);

                return false;
            }
        });
    }
}