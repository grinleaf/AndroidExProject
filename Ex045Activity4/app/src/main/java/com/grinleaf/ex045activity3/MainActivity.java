package com.grinleaf.ex045activity3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(view -> {
            //SecondActivity 클래스명을 명시하지 않고 실행하기

            //묵시적(암시적) intent 로 실행하기 : 해당 앱 뿐만 아니라, 디바이스에 설치된 모든 앱을 검토하여 aaa 식별자를 가진 Activity 를 실행함 --> 여러개면 어떤 앱 사용할지 물어봄(한번만/항상)
            Intent intent= new Intent();        //객체 생성시 파라미터 값 주지 않기
            intent.setAction("aaa");            //식별자 같은 값을 지정 --> 프로젝트의 AndroidManiFest.xml 에 적힌 식별자
            startActivity(intent);

            //클래스 명을 명시했던 '명시적 Intent' 는 같은 App 안에서만 동작이 가능함
            //Manifest 의 action 값을 이용하여 실행하는 '묵시적 Intent' 는 디바이스에 설치된 모든 앱들의 Activity 를 실행할 수 있음
            //즉, 다음 앱의 화면도 실행될 수 있음. 
            //단, api 30 버전부터 묵시적 Intent 사용 시 외부 실행 허용속성을 반드시 Manifest.xml 에 명시해야함. 경우에 따라 외부에서 실행되지 않도록 할 수 있음
        });
    }
}