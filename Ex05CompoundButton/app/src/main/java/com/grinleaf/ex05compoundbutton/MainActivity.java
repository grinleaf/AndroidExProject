package com.grinleaf.ex05compoundbutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    //1. 화면에 보여지는 뷰들의 참조변수 선언
    CheckBox cb01, cb02, cb03;
    Button btn;
    TextView tv;

    ToggleButton toggle;
    Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2. xml 에서 만든 뷰 객체를 참조변수에 대입하기
        cb01= findViewById(R.id.cb01);
        cb02= findViewById(R.id.cb02);
        cb03= findViewById(R.id.cb03);
        btn= findViewById(R.id.btn);
        tv= findViewById(R.id.tv);
        toggle= findViewById(R.id.toggle);
        sw= findViewById(R.id.sw);

        //3-1. 버튼이 클릭되면 반응하는 리스너 객체 생성 및 버튼 설정
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //버튼이 클릭되면 실행되는 중괄호 영역
                //4. 체크박스들 중에 체크상태인 CheckBox 의 text 를 가져와 TextView 에 보여주기
                String s="";    //지역변수이므로 쓰레기값이 들어감. 보통 빈문자열을 넣어둠!

                if(cb01.isChecked()) s+= cb01.getText().toString();
                if(cb02.isChecked()) s+= cb02.getText().toString();
                if(cb03.isChecked()) s+= cb03.getText().toString();

                tv.setText(s);
                }
        
        });
        
        //3-2. 체크박스의 상태가 체크로 변경되면 반응하는 리스너 객체 생성
        CompoundButton.OnCheckedChangeListener changeListener= new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //체크상태가 변경될 때마다 발동하는 콜백메소드 영역
                String s="";

                if(cb01.isChecked()) s+= cb01.getText().toString();
                if(cb02.isChecked()) s+= cb02.getText().toString();
                if(cb03.isChecked()) s+= cb03.getText().toString();

                tv.setText(s);
            }
        };

        //리스너를 체크박스에 설정
        cb01.setOnCheckedChangeListener(changeListener);
        cb02.setOnCheckedChangeListener(changeListener);
        cb03.setOnCheckedChangeListener(changeListener);

        //토글버튼의 체크상태 변경에 반응하는 리스너 객체 생성
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //메소드의 2번째 파라미터 변수인 b : 체크상태값 (true/false) 을 가짐
                
                tv.setText(b+"");   //묵시적 형변환 이용하여 boolean 값을 String 값으로 변환함
                        
            }
        });

        //스위치의 체크상태 변경에 반응하는 리스너 객체 생성
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) tv.setText("사운드허용 O");
                else tv.setText("사운드허용 X");
            }
        });

    }
}