package com.grinleaf.ex046activity5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(view -> {
            //묵시적 Intent 로 다른 앱의 액티비티를 실행하기 (Ex045 프로젝트에 미리 Manifest 액션값을 설정한 상태)
            Intent intent= new Intent();
            intent.setAction("aaa");        //Ex045 SecondActivity 의 식별자 ( exported 값이 true 이면 실행/false 이면 앱 에러 )
            startActivity(intent);
        });
    }
}