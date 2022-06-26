package com.grinleaf.ex014edittext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //뷰 객체 참조하는 참조변수 선언
    EditText et01, et02, et03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //xml 에서 만든 뷰 객체를 참조변수가 참조하도록 대입
        et01= findViewById(R.id.et01);
        et02= findViewById(R.id.et02);
        et03= findViewById(R.id.et03);
        
        //EditText 의 글씨가 변경될 때마다 반응하는 리스너 객체 생성 및 화면 설정(추가)
            //글자가 바뀌기 직전 상황 / 글자가 바뀌는 당시 상황 / 글자가 바뀌고 난후 상황을 각각 작성해야한다!
        et01.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>=3){
                    //커서를 EditText 2번으로 이동시키기 : [ et02번이 포커스(커서)를 가진다 ]
                    et02.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //커서를 EditText 3번으로 이동시키기 : [ et03번이 포커스(커서)를 가진다 ]
                if(charSequence.length()>=4) et03.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}