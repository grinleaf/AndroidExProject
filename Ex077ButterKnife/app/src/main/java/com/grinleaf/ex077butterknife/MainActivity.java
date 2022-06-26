package com.grinleaf.ex077butterknife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity {

    //findViewById() 메소드의 번거로움을 해소하기 위한 외부 라이브러리 : [ Butter Knife ] - jake wharton

    //1. 버터나이프 를 이용하여 xml 에서 id 가 "tv"인 뷰와 해당 참조변수를 연결(bind)
    @BindView(R.id.tv) TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2. 버터나이프가 연결해준 뷰들을 가진 타겟(=액티비티)을 연결 (바인드뷰-액티비티 연결)
        ButterKnife.bind(this);

        tv.setText("Nice to meet you.");
    }

    //3. ButterKnife 의 이벤트 처리 방식 (버튼 클릭 이벤트)
    @OnClick(R.id.btn)
    void clickBtn(){
        tv.setText("Have a good day.");
    }

    @OnLongClick(R.id.btn)
    void longClickBtn(){
        Toast.makeText(this, "long click", Toast.LENGTH_SHORT).show();
    }
}