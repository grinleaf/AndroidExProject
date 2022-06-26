package com.grinleaf.ex013scrollview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {

    //1. 뷰 참조변수 선언
    ScrollView scroll;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //xml 에서 만든 뷰 객체를 참조변수가 참조하도록 대입
        scroll= findViewById(R.id.scroll);
        btn= findViewById(R.id.btn);

        //버튼에 클릭 이벤트 발생 시 발동되는 리스너 객체 생성 및 화면 설정
        btn.setOnClickListener(new View.OnClickListener() {
            

            @Override
            public void onClick(View view) {  //버튼이 클릭되면 발동하는 영역
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}