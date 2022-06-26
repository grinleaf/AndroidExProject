package com.grinleaf.ex070galleryappphotopick;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv= findViewById(R.id.iv);

        findViewById(R.id.btn).setOnClickListener(v->{
            //갤러리앱 or 사진앱 실행하기
            Intent intent= new Intent();
            intent.setAction(Intent.ACTION_PICK);   //갤러리 앱 식별자
            intent.setType("image/*");  //image/png : 이미지 중 png 확장자인 파일, */* : 모든 파일, image/* : 이미지 중 모든 확장자 파일 = MIME 타입
            
            //startActivity(intent);  //요거는 사진앱 실행까지만 하고 쫑. 결과값 반환까지 받으려면?
            //startActivityForResult(intent,10);  //요거 deprecated 돼서 다른 방법 권장 중이긴 한데 현업에선 아직,,,많이 씀~
            //Android 10 버전 이후 방법 (권장하는 방법)
            
        });
    }
    
    //결과를 받기 위해 새로운 액티비티를 실행시켜주는 객체 등록 및 생성  //액티비티결과실행자<Intent 를 이용함> = 액티비티결과등록(new 액티비티결과받는 객체 생성, new 결과값 가지고 돌아오는 객체 생성);
    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            //파라미터 : 결과를 가지고 온 객체
            
            //갤러리나 사진 앱에서 사진을 선택하고 돌아왔는지부터 확인
            if(result.getResultCode()!= RESULT_CANCELED){   //RESULT_CANCELED : 뒤로가기버튼 누른 상황
                //결과를 가지고 돌아온 택배기사(Intent) 소환
                Intent intent= result.getData(); //result.getData() : Intent 가 가진 결과값을 빼옴
                Uri uri= intent.getData();                //intent.getData() : Uri 경로의 데이터를 가져옴
                //이미지뷰에 uri 경로의 사진을 설정 : [ 이미지로드 라이브러리 사용을 권장함! - Glide(어느정도 큰 용량의 이미지도 축소해서 보여줌) ]
                //iv.setImageURI(uri);
                Glide.with(MainActivity.this).load(uri).into(iv);
            }
        }
    });
    
    
}