package com.grinleaf.ex093videoviewandexoplayer;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.grinleaf.ex093videoviewandexoplayer.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    //기존 VideoView 의 성능을 개선한 라이브러리 : [ ExoPlayer Library ]

    ActivitySecondBinding binding;

    Uri videoUri= Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");

    //실제 비디오를 플레이하는 객체의 참조변수
    ExoPlayer exoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //실제 비디오를 실행하는 객체 생성
        exoPlayer= new ExoPlayer.Builder(this).build();
        //플레이어뷰에게 플레이어 설정
        binding.pv.setPlayer(exoPlayer);

        //비디오 1개 설정하기 (=비디오테이프 역할)
        MediaItem mediaItem= MediaItem.fromUri(videoUri);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();    //비디오 로딩
        exoPlayer.play();       //자동으로 prepare() 이 완료되면 play()를 실행함 --> 리스너 필요 X

        //비디오 여러개 설정하기
//        Uri firstUri= Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
//        Uri secondUri= Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4");
//        MediaItem mediaItem1= MediaItem.fromUri(firstUri);
//        MediaItem mediaItem2= MediaItem.fromUri(secondUri);
//        exoPlayer.addMediaItem(mediaItem1);
//        exoPlayer.addMediaItem(mediaItem2);
//        exoPlayer.prepare();
//        exoPlayer.play();
//        exoPlayer.setRepeatMode(ExoPlayer.REPEAT_MODE_ALL);

        //exoPlayer 는 풀스크린모드가 없음! --> 별도의 풀스크린화면을 만들어서 실행해야함
        binding.btn.setOnClickListener(v->{
            Intent intent= new Intent(this, FullscreenActivity.class);
            intent.setData(videoUri);   //putExtra() 로 넘기지 않고, setData() 로 넘길 것 (intent 당 하나만 set 가능하다는 의미)

            //전체화면 모드로 넘어갔을 때, 현재까지 재생된 위치를 함께 넘겨줘서 이어서 플레이 되도록 설정하기
            long currentPos= exoPlayer.getCurrentPosition();
            intent.putExtra("currentPos",currentPos);

            //결과를 받기 위해 액티비티 실행하기
//            startActivity(intent);
            resultLauncher.launch(intent);


        });
    }

    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()==RESULT_OK){
                Intent intent= result.getData();    //intent 객체가져올 때 getData()
                long currentPos= intent.getLongExtra("currentPos", 0);
                exoPlayer.seekTo(currentPos);
                exoPlayer.play();
            }
        }
    });

    @Override
    protected void onPause() {  //화면이 보이지 않을 때 exoPlayer 를 멈춤
        super.onPause();
        exoPlayer.pause();
    }


}