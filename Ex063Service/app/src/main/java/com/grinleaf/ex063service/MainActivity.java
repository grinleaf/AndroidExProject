package com.grinleaf.ex063service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_start).setOnClickListener(v->startMusic());
        findViewById(R.id.btn_stop).setOnClickListener(v->stopMusic());
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

    void startMusic(){
        
        //Oreo(API 26) 버전부터는 서비스를 그냥 start 명령으로 백그라운드로 돌아가게 하면 중간에 자동으로 꺼버림 (메모리 효율성 문제)
        // --> 백그라운드에 실행중인 것을 사용자에게 알리기로 함 [ Notification ] - 강제성
        //서비스 역시 foreground service 라는 개념 추가 : notification 을 보여줌
        //서비스는 백그라운드에서 작동하지만, 사용자가 이를 인식하도록 하기위해서는 반드시 Notification 과 함께 실행되도록 강제한 기법
        
        //백그라운드 작업용 Service 컴포넌트 실행
        Intent intent= new Intent(this,MusicService.class);

        if(Build.VERSION.SDK_INT>=26) startForegroundService(intent);    //26버전 이후
        else startService(intent);                                      //26버전 이전
    }

    void stopMusic(){
        Intent intent= new Intent(this, MusicService.class);
        stopService(intent);
    }

}