package com.grinleaf.ex064servicebind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    
    //뮤직 서비스 객체의 참조변수
    MusicService musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_play).setOnClickListener(view->playMusic());
        findViewById(R.id.btn_pause).setOnClickListener(view->pauseMusic());
        findViewById(R.id.btn_stop).setOnClickListener(view->stopMusic());
    }
    
    //액티비티가 화면에 보일 때, 서비스 객체 생성 및 연결하기
    @Override
    protected void onResume() {
        super.onResume();
        
        if(musicService==null){
            //MusicService 실행(객체 생성 X, Intent 보내기 O) 및 연결(bind)
            Intent intent= new Intent(this, MusicService.class);
            startService(intent);   //서비스 객체가 없을 경우 서비스 객체를 생성. --> 이후 onStartCommand() 호출
                                    //서비스 객체가 있을 경우 --> 그냥 onStartCommand() 호출

            //위 명령으로 만들어진 MusicService 객체와 연결하기
            bindService(intent,conn,0);
            //파라미터 첫번째 : onStart 했던 intent
            //파라미터 두번째 : MainActivity 와 MusicService 를 연결하는 ServiceConnection 생성-intent 가 생성함-Binder 가 Service 의 주소를 들고 넘어옴
            //파라미터 세번째 : BIND 에 관련된 설정값. startService(intent) 로 서비스 객체 생성문이 없었을 때면 AUTO_CREATE 써서 객체 생성 가능. 지금은 필요 x
        }
    }

    //bindService()로 서비스 객체와 연결하는 통로객체 --> bindService() 의 두번째 파라미터 값으로 쓰임
    ServiceConnection conn= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //파라미터 두번째 iBinder : 연결된 서비스객체에서 넘어온 Binder 객체
            MusicService.MyBinder myBinder= (MusicService.MyBinder) iBinder;
            musicService= myBinder.getMusicServiceAddress();

            Toast.makeText(musicService, "bind...", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    void playMusic(){
        if(musicService!= null) musicService.playMusic();    //뮤직서비스 참조변수가 객체를 참조하고 있지 않을 경우
    }

    void pauseMusic(){
        if(musicService!=null) musicService.pauseMusic();
    }

    void stopMusic(){       //stop 버튼 눌렀을 때 서비스 객체 null --> 뒤로가기로 어플 종료했다가 다시 켜면 객체 null 로 만드는 구문이 없으니 create 없이 start, bind 만 진행됨
        if(musicService!=null){
            musicService.stopMusic();

            unbindService(conn);
            musicService= null;
        }

        Intent intent= new Intent(this, MusicService.class);
        stopService(intent);
        
        //액티비티 종료
        finish();
    }

    @Override
    public void onBackPressed() {   //뒤로가기 눌렀을 때도 액티비티 종료
        finish();
    }
}