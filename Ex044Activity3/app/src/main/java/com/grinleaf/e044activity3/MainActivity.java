package com.grinleaf.e044activity3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv= findViewById(R.id.tv);
        findViewById(R.id.btn).setOnClickListener(view -> {
            Intent intent= new Intent(this,SecondActivity.class);
            //결과를 받기위해 액티비티 실행
            //startActivity(intent);
            startActivityForResult(intent,10);  //requestCode 는 Intent 식별 번호이므로 본인이 편한 숫자로 사용하면 됨 ㅇㅅㅇ
        });

    //########################################################################################
        //startActivityForResult() 메소드는 deprecated 되었음 (향상된 기능 추가)
        //새로 제시된 방법 : [ ActivityResultLauncher 객체 ] --> 아래 멤버변수 위치에 생성
        findViewById(R.id.btn2).setOnClickListener(view -> {
            Intent intent= new Intent(this,SecondActivity.class);
            resultLauncher.launch(intent);
        });

    //########################################################################################

    }//onCreate 메소드

    //ActivityResultLauncher 객체는 가급적 멤버변수 위치에 만들 것!
    //이 액티비티 클래스에 이 resultLauncher 객체를 등록한다는 기능 메소드를 호출하여 제작함
    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            //파라미터로 받은 결과 객체 2개의 값을 가지고 옴 : ResultCode, Intent
            if(result.getResultCode()==RESULT_OK){
                //돌아온 결과를 가진 Intent 객체에게 추가데이터 받기
                Intent intent= result.getData();
                String id= intent.getStringExtra("id");
                String pw= intent.getStringExtra("pw");

                tv.setText(id+"\n"+pw);

            }else{
                tv.setText("결과를 받지 못함");
            }
        }
    });


    //startActivityForResult() 로 실행한 Activity 가 종료되어 다시 이 액티비티가(MainActivity) 다시 보여질 때, 자동으로 실행되는 콜백 메소드
    //[ onActivityResult() : 결과값을 가지고 돌아온 Intent 를 받는 곳 ]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 10:

                if(resultCode==RESULT_OK){
                    //이 메소드의 3번째 파라미터가 결과를 가져온 택배기사(Intent) 객체의 식별 번호
                    //이 택배기사에게 추가데이터 받기
                    String id= data.getStringExtra("id");
                    String pw= data.getStringExtra("pw");

                    tv.setText(id+"\n"+pw);
                }else{
                    tv.setText("결과를 받지 못했습니다.");
                }
                break;
        }
    }
}//MainActivity