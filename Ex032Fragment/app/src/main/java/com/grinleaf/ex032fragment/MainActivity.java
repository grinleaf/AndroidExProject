package com.grinleaf.ex032fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv= findViewById(R.id.tv);
        btn= findViewById(R.id.btn);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MyFragment 의 TextView 에 접근하기 --> Activity 안에 Fragment 만을 따로 관리하는 관리자 객체 존재 [ FragmentManager - androidx ]
                FragmentManager fragmentManager= getSupportFragmentManager();   //androidx 사용 시, 새로 만들어진 Support 메소드 사용할 것~ 호환 주의

                //프레그먼트 관리자에게 프레그먼트를 찾아달라고 요청하기
                MyFragment frag= (MyFragment) fragmentManager.findFragmentById(R.id.frag_my);
                frag.tv.setText("Good Boy!!");
            }
        });
    }
}