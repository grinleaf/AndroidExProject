package com.grinleaf.ex094youtubeplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.grinleaf.ex094youtubeplayer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    //Youtube 전용 Player : 구글 개발자사이트 가이드 참고 --> [ Youtube Player API ]
    //Youtube Player API : 사이트에서 .zip 압축문서를 다운받아 압축해제하여 내 프로젝트의 libs 에 복붙하기

    YouTubePlayerFragment youTubePlayerFragment;
    YouTubePlayerFragment youTubePlayerFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //xml 에 있는 YoutubePlayerFragment 참조 : androidx 로 만들지 않았음! FragmentManger 클래스 import 시 android 클래스로 해야함~
        FragmentManager fragmentManager= getFragmentManager();
        youTubePlayerFragment= (YouTubePlayerFragment) fragmentManager.findFragmentById(R.id.youtube_fragment);

        //유튜브플레이어 초기화 = 로딩하기 (exoPlayer.prepare() 기능)
        youTubePlayerFragment.initialize("first", new YouTubePlayer.OnInitializedListener() {   //(식별자-프래그먼트 안에 유튜브가 하나가 아닐 수 있음, 리스너 객체)
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                //파라미터 ( provider, 해당 프래그먼트 내의 영상,
                //유튜브 비디오 id 를 지정 (url x) : 유튜브 영상 URL(https://www.youtube.com/watch?v=eN5mG_yMDiM) 중 ?뒤 변수에 있는 값이 식별자!
                youTubePlayer.loadVideo("eN5mG_yMDiM"); //로딩이 완료되면 자동으로 실행됨 (재생버튼 누르지 않아도)
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(MainActivity.this, "error: "+youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        youTubePlayerFragment2= (YouTubePlayerFragment) fragmentManager.findFragmentById(R.id.youtube_fragment2);
        youTubePlayerFragment2.initialize("second", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo("sLe6NBx46Ik"); //로딩이 완료되어도 대기하고 있음 (first 영상 재생 중인 상태)
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        binding.youtubeThumbnail.initialize("thumb", new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo("DHmqyWPqpv4");
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

    }
}