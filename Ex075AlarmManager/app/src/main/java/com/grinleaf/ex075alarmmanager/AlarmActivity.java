package com.grinleaf.ex075alarmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        //2-12. service 끄기
        findViewById(R.id.btn).setOnClickListener(v->{
            Intent intent= new Intent(this, AlarmService.class);
            stopService(intent);

            finish();
        });


    }
}