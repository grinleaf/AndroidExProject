package com.grinleaf.ex071cameraapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        iv= findViewById(R.id.iv);
        findViewById(R.id.btn).setOnClickListener(v->{
            //1. 카메라 앱을 실행시키는 Intent 생성
            //Intent intent= new Intent(Intent.ACTION_PICK);    //원래 Intent 생성, setAction 할때 요렇게 쓰는데, 유독 카메라 앱만 다름
            Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            
            //2. '결과를 받기 위해 액티비티를 실행시켜주는 객체' 에게 실행 작업 요청 : ActivityResultLauncher<> --> 메소드 바깥쪽에 객체 생성부터
            //4. onActivityResult 작성 후 돌아와서 실행 작업 요청하기
            resultLauncher.launch(intent);
        });
    }
    //3. 결과를 받기 위해 액티비티를 실행시켜주는 객체 생성
    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()!= RESULT_OK){ //카메라앱은 무조건 OK! 이미지는 CANCLED 이었옹
                //5. M 버전부터는 앱으로 실행한 카메라 앱으로 촬영한 사진을 곧바로 파일로 저장하지 않고, 결과를 단순 Bitmap(이미지객체)를 줌줌
                //6. 모든 디바이스가 이런 특성을 갖진 않음 --> uri 가 있는 폰 (샤오미 브랜드) : 찍은게 파일로 저장됨 / uri 가 없는 폰 (나머지 모든 폰) : 찍은게 Bitmap 결과로 반환
                
                //결과를 가져온 택배기사(Intent) 소환
                Intent intent= result.getData();
                Uri uri= intent.getData();
                //6-1. 즉, 두 상황을 아우르기 위해 조건 별로 상황 설정해야함
                if (uri==null){  //파일로 저장되어 있지 않으면, Bitmap 으로 결과를 받은 상황
                    Toast.makeText(MainActivity.this, "Bitmap으로 받음", Toast.LENGTH_SHORT).show();

                    //6-2. 인텐트의 추가데이터로 Bitmap 객체가 전달되어 옴
                    Bundle bundle= intent.getExtras();  //인텐트의 추가데이터를 번들에 데이터들 넣기 (보따리)
                    Bitmap bm= (Bitmap) bundle.get("data"); //data 라는 식별자를 가진 것 안에 bitmap 데이터가 전달되어 있음 (고정)

                    Glide.with(MainActivity.this).load(bm).into(iv);    //외부 라이브러리로 이미지 가져오기

                    //6-3. Bitmap 객체는 촬영된 사진의 섬네일 이미지임 (크게 보면 화질 매우매우~ 깨짐)
                    //--> 실제 카메라앱 만들 때는 파일로 저장되도록 미리 작업을 해놓고 실행해야함! 사실 실제로는 해당 예제대로 만들면 안됨~! --> Ex072 가 찐!
                    
                }else{  //파일로 저장되는 디바이스 (샤오미 같은 경우)
                    Toast.makeText(MainActivity.this, "uri로 받음", Toast.LENGTH_SHORT).show();
                    Glide.with(MainActivity.this).load(uri).into(iv);
                }
           }
        }
    });
}