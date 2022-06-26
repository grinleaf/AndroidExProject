package com.grinleaf.ex072cameraapp2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //0. Ex071 예제와 같이 구성
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //4-1-a. 동적 퍼미션 요청 코드
        String[] permissions= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if( checkSelfPermission(permissions[0])== PackageManager.PERMISSION_DENIED){
            requestPermissions(permissions,100);
        }

        iv= findViewById(R.id.iv);
        findViewById(R.id.btn).setOnClickListener(v->{
            Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            //3. 저장될 파일의 uri 를 설정하는 기능메소드 호출 --> 직접 만들 기능메소드! 4번으로~
            setImageUri();

            //1. 카메라 앱에게 촬영한 이미지를 파일로 저장하도록 요청하기 위해, 저장될 파일의 Uri 추가데이터를 인텐트에게 전달
            if(imgUri!=null) intent.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);    //2번 에서 만든 참조변수(null 이 아닐때) 이용
            //resultLauncher.launch(intent);

        });
    }

    //2. 카메라앱이 촬영한 사진을 저장할 파일의 경로 uri 참조변수
    Uri imgUri;

    //4. 저장될 파일의 경로 Uri 를 설정하는 기능 메소드 정의
    void setImageUri(){
        //4-1. 저장될 외부저장소의 File 경로부터 얻어오기 [ 동적 퍼미션 추가 필요 ]
        File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);   //PublicDirectory : 모든 디바이스에 고정으로 생성되어 있는 폴더(Alarms, Pictures...)
        //4-2. 저장될 파일명 정하기 ( 파일들이 서로 중복되지 않도록 네이밍)
        //File.createTempFile();   //파라미터 prefix : 접두어 / suffix : 접미어 고정 후 사이 글자를 랜덤으로 돌림 --> 보통은 날짜/시간으로 구별하긴 함!
        //4-3. 특정 날짜 포맷으로 문자열을 만들어주는 객체를 이용 : [ SimpleDataFormat ]
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd_hhmmss");  //20220323103724 --> 현재 날짜+시간으로 찍힘
        String fileName= "IMG_"+sdf.format(new Date())+ ".jpg"; //앞 뒤로 고정될 접두어/접미어
        //4-4. 경로 + 파일명.확장자 결합한 File 객체 생성
        File file= new File(path,fileName);

        //4-5. 경로 제대로 들어가는지 확인
        new AlertDialog.Builder(this).setMessage(file.getAbsolutePath()).create().show();

        //5. 경로의 정의
        //* File : 실제 파일의 절대 경로 (폴더 경로)
        //* Uri  : 파일의 DB 경로 (즉, 컨텐츠의 경로. 운영체제에서 관리. 가상의 주소)
        //--> 카메라앱은 EXTRA_OUTPUT(사진이 저장될 경로)에 절대경로인 File 이 아니라 콘텐츠 경로인 Uri 를 요구함! (★ File -> Uri 로 변환하는 과정 필요 ★)
        //--> 요기서 필요한게 변환 작업해주는 [ FileProvider ]
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.N){    //디바이스 버전 N(누각까진 요것만)
            imgUri= Uri.fromFile(file);
        }else{  //누각 버전 이후부터는 보안 문제로 Contents Provider 사용해야함
            
            //5-1. 다른 앱에게 파일의 접근을 허용해주는 FileProvider
            imgUri= FileProvider.getUriForFile(this,"com.grinleaf.ex072cameraapp2.FileProvider",file);   //두번째 파라미터 authority : FileProvider 객체의 식별 명칭 --> 디바이스 내 절대 중복x

            //5-2. FileProvider 객체 만들기 : AndroidManifest.xml 에 등록

        }

        //6. uri 가 잘 구해졌는지 확인하기
        new AlertDialog.Builder(this).setMessage(imgUri.toString()).create().show();
    }

    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()==RESULT_OK){
                //7. 개발자가 저장한 imgUri 에 촬영된 사진이 저장되어있을 것이므로, 결과 데이터 받을 필요가 없음
                Glide.with(MainActivity.this).load(imgUri).into(iv);
            }
        }
    });
    //4-1-b. 동적 퍼미션 요청 결과 콜백메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //파라미터 String[] permissions : 위에 만든 String 배열 / int[] grantResults : String 배열 각각의 퍼미션 요청 결과
        if(requestCode==100&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "사진촬영 가능", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "사진촬영 불가", Toast.LENGTH_SHORT).show();
    }
}