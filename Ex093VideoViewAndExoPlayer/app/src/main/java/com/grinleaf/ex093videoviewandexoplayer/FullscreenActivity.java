package com.grinleaf.ex093videoviewandexoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.grinleaf.ex093videoviewandexoplayer.databinding.ActivityFullscreenBinding;

public class FullscreenActivity extends AppCompatActivity {

    ActivityFullscreenBinding binding;

    ExoPlayer exoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFullscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        exoPlayer= new ExoPlayer.Builder(this).build();
        binding.pv.setPlayer(exoPlayer);
        
        //이전 Activity 에서 넘어온 Intent 에게 플레이할 VideoUri 데이터를 받기
        Intent intent= getIntent();
        Uri videoUri= intent.getData();

        MediaItem mediaItem= MediaItem.fromUri(videoUri);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();

        //현재 재생위치 받아오기 + 전체화면 실행하는 exoPlayer 에 위치 설정
        long currentPos= intent.getLongExtra("currentPos",0);
        exoPlayer.seekTo(currentPos);
        exoPlayer.play();
    }

    @Override
    protected void onPause() {      //화면에 보이지 않을 때 멈춤
        super.onPause();
        exoPlayer.pause();
    }

    @Override
    protected void onDestroy() {    //exoPlayer 를 메모리에서 완전히 제거
        super.onDestroy();
        exoPlayer.release();
    }

    @Override
    public void onBackPressed() {   //현재 위치를 다시 SecondActivity 에 전달하기
        long currentPos= exoPlayer.getCurrentPosition();
        Intent intent= getIntent();
        intent.putExtra("currentPos", currentPos);
        setResult(RESULT_OK,intent);
        finish();
    }
}