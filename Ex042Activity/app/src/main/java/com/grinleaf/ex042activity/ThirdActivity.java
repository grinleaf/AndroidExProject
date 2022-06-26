package com.grinleaf.ex042activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Third Activity");

        //제목줄의 좌측에 뒤로가기 화살표 아이콘(Up Button) 을 보이도록 설정하기
        actionBar.setDisplayHomeAsUpEnabled(true);  //홈버튼 자리를 업버튼으로 사용하겠다는 메소드
    }

    //ActionBar 의 옵션메뉴 아이템을 선택했을 때 자동으로 발동하는 콜백 메소드
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        
        switch(item.getItemId()){
            case android.R.id.home:         //android 내에서 미리 정해둔 id 값을 사용할 수 있음! up button 의 아이디
                finish();                   //옵션메뉴의 각 아이템마다 온클릭 메소드를 달지 않고, 액티비티를 종료 시켜 백 스택의 home Activity 를 보여주는 것!
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }
}