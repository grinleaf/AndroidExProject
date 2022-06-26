package com.grinleaf.ex088firebasestorage;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.grinleaf.ex088firebasestorage.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //Firebase : 서버 작업을 대신 해주는 Google 서비스
    
    //0. Firebase console 에서 '프로젝트 만들기' - 앱 연동 - 작업 순서대로 해당 프로젝트와 Firebase 연동하기 ( = 라이브러리 추가 + etc ) --> 메모장 필기 참고

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLoad.setOnClickListener(v->clickLoad());
        binding.btnSelect.setOnClickListener(v->clickSelect());
        binding.btnUpload.setOnClickListener(v->clickUpload());
    }

    void clickLoad(){
        //Firebase Storage 에 저장되어있는 이미지 파일 읽어오기

        //1. Firebase Storage 관리 객체 소환
        FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();
        
        //2. root(최상위) 참조객체 얻어오기 (웹 콘솔에서 내 Firebase 저장소의 링크 : gs://ex088firebasestoragegrinleaf.appspot.com 가 아래의 파일들을 참조하는 역할)
        StorageReference rootRef= firebaseStorage.getReference();   //참조기능

        //3. 읽어오길 원하는 파일의 참조객체 얻어오기
        StorageReference imgRef= rootRef.child("koala.jpg");   //최상위 객체의 자식으로 있는 파일의 경로
        imgRef= rootRef.child("images/newyork.jpg");           //하위 폴더가 있을 경우 경로 명시만 해주면 됨!

        //4. 파일 참조 객체로부터 이미지 URL 얻어오기 (=Firebase console 의 해당 파일 액세스 토큰 클릭 시 열리는 URL)
        if(imgRef!=null){
            //참조객체로부터 다운로드 URL 을 얻어오는 작업 수행 (비동기 방식 = 별도 스레드 방식)
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {    //다운로드 URL 이 파라미터로 전달됨
                    Glide.with(MainActivity.this).load(uri).into(binding.iv);
                }
            });
        }
    }

    void clickSelect(){
        Intent intent= new Intent(Intent.ACTION_PICK);  //사진앱
        intent.setType("image/*");
        resultLauncher.launch(intent);
    }

    //5.사진 선택의 결과를 받아오는 작업
    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()!=RESULT_OK) return;   //사진을 선택하지 않고 돌아옴 (결과 OK 가 아닐때)
            imgUri= result.getData().getData(); //결과가 OK 일 경우, imgUri 에 결과 데이터(Intent)를 얻어와서 다시 결과 데이터(Uri)를 얻어옴
            Glide.with(MainActivity.this).load(imgUri).into(binding.iv);
        }
    });

    //6. 사진앱을 통해 선택된 이미지의 콘텐츠 경로 Uri 멤버변수
    Uri imgUri= null;

    void clickUpload(){
        //7. firebase storage 에 파일 업로드하기
        //storage 는 파일의 절대경로(실제경로) 없이 Uri(콘텐츠경로)로 업로드가 가능함!

        //7-1. Firebase Storage 관리 객체 소환
        FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();

        //7-3. 파일명이 중복되면 덮어쓰기가 되므로, 중복되지 않는 이름을 선호함. 보통은 날짜, 시간
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddhhmmss");
        String fileName= "IMG_"+sdf.format(new Date())+".png";      //원래는 원본파일명에서 확장자를 얻어와야함 (콘텐츠주소 --> 절대경로를 구해서 얻어와야하기 때문에 번거로움) --> 그냥 무조건 png 로 저장
                                                                    //png : 무손실압축방식 / jpg : 손실압축방식 --> png 가 jpg 로 가는 건 데이터 손실이 있음 ㅠ
        //7-2. 저장할 파일의 위치에 대한 참조객체 얻어오기
        StorageReference imgRef= firebaseStorage.getReference("uploads/"+fileName);   //지정한 파일명으로 저장됨 + 지정한 폴더 안에 저장됨 (파일, 폴더가 없을 경우 새로 생성+덮어쓰기)

        //7-4. 선택한 이미지(imgUrl)를 imgRef 참조객체가 참조하는 파일위치에 저장(업로드)
        UploadTask uploadTask= imgRef.putFile(imgUri); //putFile() 시 UploadTask 를 리턴함
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MainActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
            }
        });

    }
}