package com.grinleaf.ex043activity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText etName, etAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName= findViewById(R.id.et_name);
        etAge= findViewById(R.id.et_age);
        findViewById(R.id.btn).setOnClickListener(view -> {

            //SecondActivity 로 화면 전환하면서 보낼 데이터들            
            //EditText 에 쓰여진 데이터 값들을 얻어오기
            String name= etName.getText().toString();
            int age= Integer.parseInt(etAge.getText().toString());
            
            //SecondActivity 를 실행시켜 줄 택배기사 객체 Intent 생성
            Intent intent= new Intent(this,SecondActivity.class);

            //SecondActivity 에 전달할 데이터를 택배기사(intent)에게 추가하기
            intent.putExtra("name",name);
            intent.putExtra("age",age);
            
            startActivity(intent);
            
        });

    }
}