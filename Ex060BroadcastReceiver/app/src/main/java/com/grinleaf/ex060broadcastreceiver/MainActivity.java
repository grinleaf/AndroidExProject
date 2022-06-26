package com.grinleaf.ex060broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //*BroadcastReceiver-브로드 캐스트 리시버에 대한 수신 연습용 예제
    //*실무에서는 OS(=Android)가 방송(Broadcast)를 해야하지만, 해당 예제는 1. 버튼을 눌러서 방송을 직접 수행/2. 이를 수신하는 것으로 연습!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(v->{
            //방송 보내기 작업 : 4대 컴포넌트 중 Activity, Service, BroadcastReceiver 셋은 모두 [ Intent ] 로 실행함 !

            //BroadcastReceiver 를 상속하는 MyReceiver 설계
            //4대 컴포넌트는 반드시 AndroidManifest.xml 문서에 등록해야 사용 가능함

            //1. 명시적 Intent 를 이용하는 방법 : 리시버 자체를 직접 지정하여 방송  --> 같은 앱 안에 있는 리시버만 방송을 들을 수 있음
//            Intent intent= new Intent(this, MyReceiver.class);
//            sendBroadcast(intent);      //원래 intent 보낼 때는 startActivity(); 썼었쥥
            

            //2. 묵시적(=암시적) Intent 를 이용하는 방법 : 채널을 지정  --> 디바이스 내의 모든 앱들이 방송을 들을 수 있음
            //*Oreo(API 26) 버전부터 암시적 intent 는 오직 시스템 인텐트만 가능하도록 변경됨 --> OS 만 방송할 수 있는 Intent (개발자가 임의로 만들어내는 신호 주고받기 작업 제재)
            //*암시적 인텐트를 하고싶으면, 암시적 인텐트를 동적으로 듣는 리시버 만들기

            //aaa 라는 이름의 방송 송출하는 작업
            Intent intent= new Intent();
            intent.setAction("aaa");

            sendBroadcast(intent);
        });
    }//onCreate method

    MyReceiver receiver;

    //이 액티비티가 화면에 보여질 때 자동으로 발생하는 라이프사이클 메소드 --> onCreate() / onStart() / onResume() / onPause() / onDestroyed() / 하나 모엿지
    @Override
    protected void onResume() {
        super.onResume();

        //"aaa"라는 암시적 인텐트 방송을 수신하는 리시버 동적 등록 == AndroidManifest.xml 의 <receiver> 작업을 자바코드로 작성한 것
        receiver= new MyReceiver();

        IntentFilter filter= new IntentFilter();
        filter.addAction("aaa");

        registerReceiver(receiver,filter);
    }

    //액티비티가 안보이기 시작할 때 자동으로 발동하는 라이프사이클 메소드
    @Override
    protected void onPause() {
        super.onPause();

        //리시버 제거
        unregisterReceiver(receiver);
    }
}