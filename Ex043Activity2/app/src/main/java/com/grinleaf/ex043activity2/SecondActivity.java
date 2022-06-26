package com.grinleaf.ex043activity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //SecondActivity 를 실행시켜준 택배기사를 소환 (추가 데이터를 받기 위함)
        Intent intent= getIntent();

        //추가데이터 얻어오기
        String name= intent.getStringExtra("name");
        int age= intent.getIntExtra("age",-1);  //값이 제대로 전달되지 않았을 경우 대입될 값 -1

        tv= findViewById(R.id.tv);
        tv.setText(name+"\n"+age);
    }
}