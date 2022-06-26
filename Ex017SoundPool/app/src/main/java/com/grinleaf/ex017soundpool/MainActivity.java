package com.grinleaf.ex017soundpool;

import androidx.appcompat.app.AppCompatActivity;

import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //뷰들의 참조변수
    Button btnStart, btnAgain, btnGoodJob, btnMusic;

    //사운드풀 참조변수
    SoundPool sp;

    //등록된 사운드 음원들의 식별번호 저장 변수
    int sdStart, sdAgain, sdGoodJob, sdMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //사운드풀(음악플레이어) 객체 생성하여 음원 등록
        SoundPool.Builder builder= new SoundPool.Builder();
        builder.setMaxStreams(10);     //동시에 들리는 소리(한번에 플레이 가능한 음원) 최대 개수(10개를 넘지 않게 하는 것이 보통이다)
                                       //10개가 넘어가면 우선순위가 낮은 소리는 들리지 않음
        //빌더 이용하여 사운드풀 객체 생성
        sp= builder.build();

        //사운드풀에 음원을 하나씩 등록하면 자동으로 음원을 구분하는 식별번호(id)가 리턴됨
        sdStart= sp.load(this,R.raw.s_sijak,0);//load(context=view 대리인, 시작음원, 우선순위)
        sdAgain= sp.load(this,R.raw.s_again,0);
        sdGoodJob= sp.load(this,R.raw.s_goodjob,0);
        sdMusic= sp.load(this,R.raw.kalimba,0);

        btnStart= findViewById(R.id.btn_start);
        btnAgain= findViewById(R.id.btn_again);
        btnGoodJob= findViewById(R.id.btn_goodjob);
        btnMusic= findViewById(R.id.btn_music);
        
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //s_sijak.mp3 음원을 등록하여 받은 식별번호(sdStart)를 이용하여 효과음 플레이
                sp.play(sdStart,0.9f,0.9f,3,0, 1); //볼륨 0.0~1.0 사이로 넣을 것. float 형이므로 f 붙여주기
            }
        });

        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.play(sdAgain, 0.9f,0.9f,2, 1,3);
            }
        });

        btnGoodJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sp!=null) sp.play(sdGoodJob, 1,1,0,0,1);
            }
        });

        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sp!=null) sp.play(sdMusic,1,1,5,0,1);
            }
        });

    }
}