package com.mrhi2022.ex035toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar= findViewById(R.id.toolbar);
        //이 액티비티의 제목줄(ActionBar)를 위에 찾아온 Toolbar로 대체하여 설정하기
        setSupportActionBar(toolbar);

        //제목줄(ActionBar)에 표시되는 제목글씨 안보이도록 설정
        //제목줄 객체를 소환
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

    }

    //옵션메뉴를 만드는 코드를 작성하는 콜백메소드
    //기본 Toolbar의 테마로 인해 menu아이템의 색상은 어두운 회색임.[테마로 지정해야함]
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return super.onCreateOptionsMenu(menu);
    }
}