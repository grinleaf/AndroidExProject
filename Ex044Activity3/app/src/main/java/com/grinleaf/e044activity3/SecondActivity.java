package com.grinleaf.e044activity3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    EditText etId, etPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        etId= findViewById(R.id.et_id);
        etPw= findViewById(R.id.et_pw);
        findViewById(R.id.btn).setOnClickListener(view->{
            //나에게 왔던 택배기사(Intent)에게 데이터들(id,pw)을 넣어서 되돌려 보내기

            //1.돌려보낼 글씨 데이터를 EditText 로부터 얻어오기
            String id= etId.getText().toString();
            String pw= etPw.getText().toString();
            
            //2.나에게 온 택배기사(Intent)를 소환하여 추가데이터로 넣기
            Intent intent= getIntent();
            intent.putExtra("id",id);
            intent.putExtra("pw",pw);

            //3.결과를 주었다고 명시해야함! ★
            setResult(RESULT_OK,intent);    //result code 도 임의로 부여할 수 있으나, 상수로 지정되어 있는 것들이 있음. RESULT_OK/RESULT_CANCLED...
                                            //사용자가 값을 다 입력하지 않고 뒤로가기 눌렀을 경우 설정

            //4.이 SecondActivity 를 종료
            finish();
        });
    }
}