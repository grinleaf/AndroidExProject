package com.grinleaf.ex093videoviewandexoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;

import com.grinleaf.ex093videoviewandexoplayer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    //Video 파일은 용량이 크기 때문에 대부분의 경우 res 폴더에 넣고 사용하지 않음. res - raw 폴더에 비디오, 오디오 파일 위치 (권장 X)
    //보통 서버에 비디오 파일을 저장하고 이를 불러와서 플레이함

    //sample video url 를 검색
    Uri videoUri= Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //비디오뷰에 컨트롤바 붙이기 : 컨트롤바는 무조건 액티비티에 붙음. 내부적으로 별도 스레드로 작업 수행되기 때문에 프로그램이 무거워짐 --> 라이브러리로 대체 [ Exo Library ]
        binding.vv.setMediaController(new MediaController(this));

        //VideoView 에 동영상 uri 설정하기
        binding.vv.setVideoURI(videoUri);
        //비디오뷰가 재생되려면 start() 를 해줘야 함! 단, 동영상을 로딩하는 시간이 있으므로, 바로 하지 않고 가급적 비디오의 플레이 준비가 완료되었을 때 명령할 것을 권장
        //+ 인터넷 사용 퍼미션 추가
        binding.vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                binding.vv.start();
            }
        });

        binding.btn.setOnClickListener(v->{
            startActivity(new Intent(this, SecondActivity.class));
            finish();
        });
    }

    @Override
    protected void onPause() {      //화면이 안 보이기 시작할 때
        super.onPause();

        //비디오 일시정지
        if(binding.vv.isPlaying()) binding.vv.pause();
    }
}