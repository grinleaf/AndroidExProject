package com.grinleaf.ex021searchviewmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    //옵션메뉴의 메뉴항목 안에 있는 SearchView 의 참조변수
    SearchView searchView;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    //뷰를 보여주는 onCreate 메소드 종료 후, 자동으로 옵션 메뉴를 만들기 위해 발동하는 콜백 메소드 : [ onCreateOptionsMenu() ]

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //res/menu 폴더 안의 option.xml 을 읽어와서 메뉴 객체로 생성해주는 Inflater
        getMenuInflater().inflate(R.menu.option, menu);     //파라미터 menu 에서 검색바가 뜸(키보드의 액션키가 돋보기 모양=줄바꿈 불가능)

        //메뉴 안에 있는 메뉴항목 참조하기
        MenuItem item= menu.findItem(R.id.menu_search);
        searchView= (SearchView) item.getActionView();

        //힌트 글씨 변경하기
        searchView.setQueryHint("Input word...");
        searchView.setIconified(false);     //검색창이 열려있는 상태로 시작
    
        //소프트키패드(=키보드)의 검색버튼(돋보기모양)을 클릭했을 때 반응하는 리스너 객체 생성 및 설정
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            
            //소프트키패드(=키보드)의 돋보기 버튼을 클릭했을 때 발동되는 콜백 메소드
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this,query+"를 검색합니다.",Toast.LENGTH_SHORT).show();
                //혹시 SearchView 에 써있는 글씨가 없어지거나, 원하는 글씨로 미리 설정하고 싶을 때,
                searchView.setQuery("",true);
                //검색 후 바로 아이콘 모양으로 돌아가고 싶을 때
                searchView.setIconified(true);  //검색창이 닫힘
                return false;
            }

            //글씨가 변경될 때마다 실행되는 콜백 메소드 (검색창에 한글자 추가기입할 때마다 아래 추천검색어 변경되는 것처럼!)
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}