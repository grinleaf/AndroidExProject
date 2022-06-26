package com.grinleaf.ex047activitysystemintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.b1).setOnClickListener(view -> {
            //Dial 화면 앱 실행하기
            Intent intent= new Intent();
            intent.setAction(Intent.ACTION_DIAL);     //웬만한 기본 앱들의 식별자(액션값)는 고정되어 있음 SDK 폴더에 모아둠 ㅎ_ㅎ 우리는 Intent. 으로 편집기한테 찾아달라하장

            //미리 전화번호까지 선택 가능 : Uri 클래스 = URL, FTP 등 인터넷주소, 파일주소 등을 관리
            Uri uri= Uri.parse("tel:01012345678");
            intent.setData(uri);

            startActivity(intent);

        });

        findViewById(R.id.b2).setOnClickListener(view -> {
            //짧은 문자 보내기 = SMS
            Intent intent= new Intent();
            intent.setAction(Intent.ACTION_SENDTO);

            //미리 짧은 문자를 보낼 번호를 선택 해두기
            Uri uri= Uri.parse("smsto:01098765432, 01054321234");   //여러 명에게 보내기도 가능
            intent.setData(uri);
            //미리 짧은 문자의 내용을 작성해두기
            intent.putExtra("sms_body","Hello~!~!~!~!~!~!~!~!~!~");   //문자 내용란에 값을 put 하기

            startActivity(intent);
        });

        findViewById(R.id.b3).setOnClickListener(view -> {
            //Web Site = 웹 브라우저 열기
//            Intent intent= new Intent();
//            intent.setAction(intent.ACTION_VIEW);   //거의 대부분의 앱이 이 ACTION_VIEW 로 실행된댕. 웹사이트 뿐만 아니라
            Uri uri= Uri.parse("http://www.naver.com");
            Intent intent= new Intent(Intent.ACTION_VIEW,uri);
            //intent.setData(uri);
            startActivity(intent);          //요렇게 3줄로 축약 가능
        });

        findViewById(R.id.b4).setOnClickListener(view -> {
            //사진(갤러리) 앱 열기
            Intent intent= new Intent(Intent.ACTION_PICK);
            intent.setType("image/png");        //얘는 Uri 데이터 대신 필수적으로 파일의 타입을 알려줘야함. 확장자를 모르면 "image/*" 을 기입

            //디바이스, OS 버전에 따라 startActivityForResult(); 로 코드 작성해야만 실행되는 경우도 있음.
            startActivity(intent);
            //선택한 사진을 받아서 바로 보여주는 기능은 나중에 할 예정 ~!~!~!~!

        });

        findViewById(R.id.b5).setOnClickListener(view -> {
            //카메라 앱 열기
            Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);     //동영상 앱 열고 싶으면 ACTION_VIDEO_CAPTURE 으로 쓰면 끝!
            startActivity(intent);
            //다른 앱에서 열린 카메라 앱으로 찍은 사진을 보여주는 기능도 나중에 할 예정 ~!~!~!!~!
        });

        //안드로이드 개발자 사이트 or 구글 검색을 통해 기타 시스템 Intent ACTION 기능들을 확인해가면서 사용할 것! 다 못 외워 ^_____^....
    }
}