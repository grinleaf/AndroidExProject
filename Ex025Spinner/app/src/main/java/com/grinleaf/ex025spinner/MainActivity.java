package com.grinleaf.ex025spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner= findViewById(R.id.spinner);

        //스피너의 항목 모양을 미리 디자인(xml)하고 싶을경우 직접 아답터를 만들어 적용
        adapter= ArrayAdapter.createFromResource(this, R.array.city, R.layout.spinner);
        //스피너에게 Adapter 설정
        spinner.setAdapter(adapter);
        
        //드롭다운된 항목들의 모양만 별도로 지정 가능
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);


        //스피터의 항목 선택 시 반응하는 리스너 객체 생성 및 설정
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, i+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}