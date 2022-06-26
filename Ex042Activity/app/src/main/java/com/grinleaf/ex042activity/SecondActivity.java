package com.grinleaf.ex042activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    //해당 액티비티가 보여줄 뷰를 설정하는 코드 작성을 위한 콜백 메소드 : [  ]
    //액티비티가 처음 생성될 때 자동으로 실행되는 메소드
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //제목줄 관리 객체 소환
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Second Activity");
    }
}
// 액티비티를 새로 만들었으면 AndroidManifest.xml 문서에 해당 액티비티의 존재를 알리는 xml 코드를 작성해줘야 인식함~