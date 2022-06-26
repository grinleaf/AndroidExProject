package com.grinleaf.ex073cameraappvideo;

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
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    VideoView vv;
    Button btn; //1. 퍼미션을 받으면 눌러지고, 퍼미션을 못받으면 눌러지지 않게(버튼 비활성화 상태로 보여짐-회색) 설정하기 위해 멤버로 만들어둔 것!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vv= findViewById(R.id.vv);
        btn= findViewById(R.id.btn);
        btn.setOnClickListener(v->clickBtn());

        //3. 동적 퍼미션 체크
        String[] permissions= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(checkSelfPermission(permissions[0])== PackageManager.PERMISSION_DENIED){
            requestPermissions(permissions, 10);
            //퍼미션이 허가되어 있지 않으면 버튼 사용하지 못하도록 작성
            btn.setEnabled(false);
        }
    }

    //4. 리퀘스트퍼미션 결과 받기
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==10 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "카메라 사용가능", Toast.LENGTH_SHORT).show();
            btn.setEnabled(true);
        }else{
            Toast.makeText(this, "카메라 사용불가", Toast.LENGTH_SHORT).show();
            btn.setEnabled(false);  //얘는 위에서 해놓고 왔어서 사실 안써두 되는뎀 ㅇㅅㅇ 가시성!
        }
    }

    //5. 비디오가 저장될 uri
    Uri videoUri;

    //6. 기능메소드
    void setVideoUri(){
        //6-1. 외부 저장소의 공용영역에 특정 파일명으로 저장
        File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES); //저장될 파일 경로(공용 영역)
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd_hhmmss");
        String fileName= "VIDEO_"+sdf.format(new Date())+".mp4";    //오늘 날짜,시간으로 포맷 + 접두어/접미어 --> 파일명 으로 저장됨
        File file= new File(path, fileName);    //(저장될 경로, 파일명) --> 파일 객체 생성
        
        //6-2. file --> uri 로 변환
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.N){    //디바이스의 버전이 N(누각) 보다 낮을 때
            videoUri= Uri.fromFile(file);
        }else{  //버전이 높을 때 --> authority : 패키지명+ .FileProvider 때문에 사용하는 식별자인 것을 명시해줄 것!
            videoUri= FileProvider.getUriForFile(this,"com.grinleaf.ex073cameraappvideo.FileProvider",file);
        }

        //확인용 다이얼로그
        //new AlertDialog.Builder(this).setMessage(videoUri.toString()).create().show();

    }
    
    void clickBtn(){
        Intent intent= new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        setVideoUri();  //6. 기능 메소드 직접 만들기 (위로위로)
        if(videoUri!=null) intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);

        //7. 결과를 받기 위한 실행
        resultLauncher.launch(intent);
    }

    //7-1. 결과를 받기위해 실행하는 객체 생성
    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            //7-2. 런처의 실행이 끝날 때
            if(result.getResultCode()==RESULT_OK){
                vv.setVideoURI(videoUri);   //VideoView 에 videoUri 설정(set)

                //8. 비디오뷰에 컨트롤바 설정 : 비디오뷰를 눌렀을 때 컨트롤 패널이 나오도록 (위치 조정은 다음 수업 시간에! - 현업에서는 비디오뷰 안쓰구 외부라이브러리 EXO 를 쓴당)
                MediaController controller= new MediaController(MainActivity.this);
                vv.setMediaController(controller);

                //vv.start();
                //7-3. 바로 스타트 하면 video 용량이 클 경우 실행되지 않을 가능성 有. video 의 로딩, 준비가 끝났을 때 start 하도록 설정해야함 --> 리스너 객체
                vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { //7-4. 준비완료 되었을 때를 듣는 리스너 객체 생성
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        Toast.makeText(MainActivity.this, "준비완료!", Toast.LENGTH_SHORT).show();
                        vv.start();
                    }
                });
            }
        }
    });
}