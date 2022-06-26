package com.grinleaf.ex015toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //뷰 객체 참조변수
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //뷰객체 참조변수 대입
        btn= findViewById(R.id.btn);

        //버튼 클릭 시 작동하는 리스너 객체
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              Toast t= Toast.makeText(MainActivity.this, "clicked",Toast.LENGTH_SHORT);
//              t.show();

                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}