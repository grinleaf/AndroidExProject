package com.grinleaf.ex042activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1= findViewById(R.id.b1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //b1 버튼을 누르면 Second Activity 를 실행시키기
                Intent intent= new Intent(MainActivity.this,SecondActivity.class);  //파라미터 (Context, Class<?> cls) : .class 해주면 해당 클래스의 정보를 보낼 수 있음!
                startActivity(intent);    //파라미터 Intent intent : Second Activity 를 대신 실행시켜주는 택배기사같은 역할의 객체 생성 ㅇㅅㅇ

                //해당 액티비티를 종료하는 기능 메소드 호출 : 원래라면 버튼을 눌렀을 때 MainActivity 는 SecondActivity 아래에 깔림 (Back Stack 에 저장) --> 뒤로가기 눌렀을 때 MainActivity 가 다시 나타남
                //버튼 실행 하여 SecondActivity 출력 후 뒤로가기 누르면 바로 앱 종료됨
                finish();   
            }
        });
        
        //람다식 표현을 사용하여 버튼 참조변수, find, onClickListener(new) 등의 과정을 줄이기!
        //람다식 : 리스너같은 인터페이스 중에서 메소드가 1개인 메소드들을 익명클래스로 만들어 사용하는 코드 축약표현
        findViewById(R.id.b2).setOnClickListener(view -> {  //변경할 수 있는 것(여기서는 파라미터의 View view 만을 변경 가능)만 기재하기

            //ThirdActivity 를 실행하기 : 람다식을 사용한 중괄호 영역 안의 this 는 리스너 객체를 가리키지 않고, MainActivity 를 가리키는 this 가 됨!
            Intent intent= new Intent(this,ThirdActivity.class);
            startActivity(intent);
            
        });

    }//onCreate() 메소드
}