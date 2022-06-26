package com.grinleaf.ex048activitylifecyclemethod;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    //Activity 의 Lifecycle Method : 액티비티 클래스 객체가 생성되어 화면에 보여지고, 종료되어 메모리에서 사라질 때까지 상황(조건)에 맞게 자동으로 실행되는 생명주기

    //1. 액티비티가 처음 메모리에 만들어질 때 자동으로 실행되는 콜백 메소드 - 이 메소드 작업 중에는 아직 화면에 어떤 뷰도 보이지 않는 상태임 : [ onCreate() ]
        //화면
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //안드로이드 스튜디오의 아래쪽 툴바에 [Logcat] 창에 기록을 남기는 기능 - 로그 남기기
        Log.i("MY", "onCreate");            //i : information / e : error / d : development / w : warning 상황의 로그를 남기고자 함
    }

    //2. 액티비티의 뷰들이 보이기 시작할 때 자동으로 실행되는 콜백메소드 : [ onStart() ]
    @Override
    protected void onStart() {
        super.onStart();

        Log.i("MY","onStart");
    }

    //3. 액티비티의 UI 가 모두 완전히 보이고, 터치에 반응하는 상태까지 완료되었을 때 자동으로 실행되는 콜백 메소드 : [ onResume() ]
        //보통 이 메소드 안에서 서버나 DB의 데이터를 읽어오는 작업을 수행
    @Override
    protected void onResume() {
        super.onResume();

        Log.i("MY","onResume");
    }

  //########################################################################################
    // 1,2,3 메소드가 실행되면 액티비티는 '실행중' 상태가 됨 : [ Running 상태 ]
  //########################################################################################

    //4. 어떤 이유에서든 이 액티비티가 화면에서 보이지 않기 시작하면 자동으로 실행되는 메소드 : [ onPause() ]
        //보통 이 메소드 안에서 게임 화면이 일시정지되는 코드를 작성하곤 함
    @Override
    protected void onPause() {
        super.onPause();

        Log.i("MY","onPause");
    }

    //5. 액티비티가 화면에서 완전히 보이지 않을 때 자동으로 실행되는 메소드 : [ onStop() ]
    @Override
    protected void onStop() {
        super.onStop();

        Log.i("MY","onStop");
    }

  //########################################################################################
    // Activity 가 다른 Activity 에 의해 가려진 상태이면, 4, 5 메소드 까지만 발동함
  //########################################################################################

    //6. 디바이스의 '뒤로가기' 버튼을 눌렀거나, finish() 메소드에 의해 액티비티가 완전히 메모리에서 사라지면 자동으로 실행되는 콜백 메소드 : [ onDestroy() ]
        //Android 12 버전에서 <intent_filter> 안에 .MAIN, .LAUNCHER 를 가진 액티비티는 '뒤로가기' 버튼을 눌러도 액티비티 종료 X. 홈화면 버튼 누른 것처럼 가려지기만 함!
    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("MY","onDestroy");
    }
    
    //Logcat 보는 방법 : 기기, 환경 선택 후 검색창에 "MY" 검색하면 해당 로그만 볼 수 있음. 애뮬레이터에서 앱을 켜고/끄고/화면전환을 하는 활동마다 상태(로그) 기록됨
}