package com.grinleaf.ex074cameraapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.camera.view.video.OutputFileOptions;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //0. 안드로이드 개발자 사이트 - Jetpack - 시작하기 - 가이드 읽어볼 것 (androidx, CameraX 등의 라이브러리 지원하는 패키지)
    //   앱 개발하다가 호환성 문제로 이전 버전 디바이스에서 앱 실행이 안 되거나 하면, 기존 뷰에 Compat 붙이면 해결됨!
    //   --> 요게 제트팩에서 지원해주는 기능이얌. 호환성 문제 보완

    //1. CameraX Jetpack Library : CAMERA API 를 쉽게 다루기 위한 새로운 라이브러리. cameraX library 부터 적용됨 - build.gradle (개발자가이드 참고)
    // --> 2. build.gradle 문서로

    //5. 프리뷰를 보여주는 뷰 참조변수
    PreviewView pvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //6. 카메라, 오디오 녹음(동영상촬영 퍼미션 필요), 외부저장소에 대한 퍼미션 등록 필요 (= 셋 다 동적 퍼미션) + 퍼미션 허가 받았는지 확인하는 코드
        String[] permissions= new String[]{Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(checkSelfPermission(permissions[0])== PackageManager.PERMISSION_DENIED){
            requestPermissions(permissions,0);
        }else{
            //7. 프리뷰 시작 --> 기능 메소드 만들기(아래아래)
            startPreview();
        }

        //7-2. 프리뷰를 보여주는 뷰
        pvv= findViewById(R.id.pvv);
        
        //8. 플로팅 버튼에 이미지 캡쳐 기능 주기 --> 기능 메소드 작성(아래아래)
        findViewById(R.id.fab).setOnClickListener(v->captureImage());
        //8-1-b. 이미지 캡처기능을 가진 객체 생성 --> 8-1-c. (아래아래)
        imageCapture= new ImageCapture.Builder().build();
    }
    
    //7-1. 기능 메소드 만들기
    void startPreview(){
        //7-1-a. 카메라 기능 사용 요청 및 가능여부를 듣는 리스너 객체 (카메라 기능 제공자를 /들을수 있는 피쳐 리스너 객체) 생성
        ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture= ProcessCameraProvider.getInstance(this);

        //7-1-b. 카메라 사용가능여부를 듣는 리스너 객체에게 리스너 추가
        cameraProviderListenableFuture.addListener(new Runnable() { //7-1-c. addListener 첫번째 파라미터 : Runnable listener=별도의 스레드로 작업
            @Override
            public void run() {
                try {
                    //7-1-e. 카메라 기능 제공자 객체 소환
                    ProcessCameraProvider cameraProvider= cameraProviderListenableFuture.get(); //카메라 기능 제공을 듣는 리스너

                    //7-1-f. 프리뷰 객체 생성
                    Preview preview= new Preview.Builder().build();    //프리뷰를 만들어주는 빌더 != 프리뷰를 보여주는 뷰를 만드는 것이 아님
                    //7-1-g. Surface(서피스) 라는 고속버퍼뷰 설정
                    //컴퓨터가 메인으로 고속주사방식에 따라 최신 폰들 카메라 화소(거의 24억 비트 넘는..화소)를 깨지지 않게 표현하기 위해서 비트를 하나하나 하드웨어로 넘기지 않고,
                    //메모리 내부에서 미리 보여줄 한 화면을 완성 시킨 후에 하드웨어 간 교환을 통해 카메라로 넘겨주는 방식을 사용함 --> 이때 화면 구성해주는 애가 서피스!
                    preview.setSurfaceProvider(pvv.getSurfaceProvider());

                    //7-1-j. 기존 카메라 제공자와 이미 연결되어있는 것이 있을 경우 연결을 끊는 과정
                    cameraProvider.unbindAll();

                    //7-1-i. 카메라 종류 선택 --> bindToLifecycle 의 두번째 파라미터로 사용
                    CameraSelector cameraSelector= CameraSelector.DEFAULT_BACK_CAMERA;

                    //7-1-h. 카메라 제공자에게 preview 연결 : 카메라 셔터가 열리면 메인도 열리고, 셔터가 닫히면 메인 액티비티도 닫히게 만들기
                    cameraProvider.bindToLifecycle(MainActivity.this,cameraSelector,preview);   //카메라를 선택하여 preview 에 부여(연결)하는 과정

                    //8-1-c. 이미지캡처와 카메라도 같이 연결
                    cameraProvider.bindToLifecycle(MainActivity.this,cameraSelector,preview,imageCapture);

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));  //7-1-d. addListener 두번째 파라미터 : executor = 메인스레드의 능력을 주는 기능(UI 변경작업 가능)
    }
    
    //8. 퍼미션 요청 결과 반응하기
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0){
            //퍼미션 중에 하나라도 허가하지 않으면 미리보기 x
            for (int result : grantResults){
                if(result==PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "카메라 API를 사용할 수 없습니다.\n해당 앱을 종료합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                    //앱 종료 : 이 때 바로 종료되지 않고, 앱 종료 시간이 걸림. finish() 작업 종료 전에 반복문 등 다른 작업들이 완료되어 버릴 수 있음
                    return; //다른 작업 진행되지 않게 메소드 종료시키기
                }
            }//for 문
            Toast.makeText(this, "카메라 API 사용가능", Toast.LENGTH_SHORT).show();
            startPreview();
        }
    }

    //8-1-a. 이미지 캡처 기능을 가져올 객체 참조변수 --> 8-1-b. (위로위로)
    ImageCapture imageCapture;

    //8-2. 플로팅 버튼에 줄 이미지 캡처 기능 메소드 : android 12 (api 31) 버전에서는 이미지 캡처가 안되는 상태.. 버전업을 기다려야함니다~ 우회하거나
    void captureImage(){
        //캡처한 이미지를 저장할 경로와 파일명
        File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMss_hhmmss");
        String fileName= "IMAGE_"+sdf.format(new Date())+".jpg";
        File file= new File(path, fileName);

        ImageCapture.OutputFileOptions outputFileOptions= new ImageCapture.OutputFileOptions.Builder(file).build();
        
        //8-3. 위에서 만든 옵션 경로에 현재 프리뷰의 이미지 데이터를 캡처하여 저장
        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {   //캡처 시 세이브 성공했을 때
                //8-4. 이미지 뷰에 설정 : uri 로 바꿔서 설정 O / 파일경로 그대로 설정 O
                //8-4-a. 파일경로 그대로 설정
                //Glide.with(MainActivity.this).load(file).into(iv);

                //8-4-b. uri 로 바꿔서 설정
                ImageView iv= findViewById(R.id.iv);
                //저장된 경로 uri 가져오기
                Uri savedUri= outputFileResults.getSavedUri();
                Glide.with(MainActivity.this).load(savedUri).into(iv);

            }
            @Override
            public void onError(@NonNull ImageCaptureException exception) { //캡처 시 에러났을 때
                Toast.makeText(MainActivity.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}