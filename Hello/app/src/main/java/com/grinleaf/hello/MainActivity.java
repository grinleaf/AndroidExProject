
package com.grinleaf.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //액티비티는 객체를 생성하면 자동으로 호출되는 메소드들이 있다!
    //액티비티가 생성되어 메모리에서 없어질 때까지 상황에 따라 자동으로 실행되는 콜백메소드 [ 라이프사이클 메소드 ]
    //onCreate() 메소드도 이 메소드 중 하나! 메인 액티비티 객체에서 생성자메소드를 굳이 쓰지 않기 때문에,
    //객체 생성과 동시에 실행해야하는 작업들을 onCreate()가 해결해준다!
    //이 onCreate() 메소드 안에서 화면에 보여질 내용물을 설정함~

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //강제로 앱이 다운되었을 때, 이전 작업상태 복구하는 것
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);  //res폴더 - layout폴더 안의 activity_main.xml 파일 미리보기(View기능을 가진 xml문서의 위치를 지정)


        //todo : 로그인 작업! //북마크는 f11
        //실제 폰에서 개발자 모드 설정방법 : 휴대폰정보 - 소프트웨어정보 - 빌드번호 7번 클릭 - 개발자옵션 활성화
        //개발자옵션 - USB 디버깅 ON - 컴퓨터와 디바이스 선 연결 - 디바이스에서 USB 디버깅 허용(컴퓨터 영구허용)
        //안드로이드 스튜디오에서 연결된 휴대폰 설정 가능

    }
}