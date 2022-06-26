package com.grinleaf.ex064servicebind;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

//Android 4대 컴포넌트는 AndroidManifest.xml 에 등록해야 사용가능!
public class MusicService extends Service {

    MediaPlayer mp;


    @Override
    public void onCreate() {
        super.onCreate();
        //서비스 객체가 처음 생성될 때 호출되는 영역
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {      //startService(intent) 명령이 떨어질 때 호출되는 영역
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //이번 예제 핵심 메소드~! Ex063 에선 안쓰고 넘어갔었쥐
    //bindService() 명령으로 서비스와 액티비티가 연결되면(ServiceConnection 생성-유일하게 Binder 만 지나다닐 수 있는 길(Service->Main 으로. Service 의 주소를 든 상태로)) 자동으로 발동하는 메소드
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();    //MainActivity 의 conn(=ServiceConnection) 에 넘어갈 객체
    }

    //conn 에 넘어갈 객체 클래스 : conn 에는 Binder 객체만 넘어갈 수 있으니, 상속시켜주기!
    class MyBinder extends Binder {
    //이 서비스객체의 주소를 리턴하는 기능메소드
        public MusicService getMusicServiceAddress(){
            return MusicService.this;
        }
    }


    // ## MediaPlayer 객체의 제어용 메소드 3개 ! ##
    public void playMusic(){
        if(mp==null){
            mp= MediaPlayer.create(this,R.raw.dragon_flight);
            mp.setVolume(0.7f,0.7f);
            mp.setLooping(true);
        }
        if(!mp.isPlaying()) mp.start(); //플레이 중이 아니면 시작
    }

    public void pauseMusic(){
        if(mp!=null&&mp.isPlaying()) mp.pause();
    }

    public void stopMusic(){
        if(mp!=null) {
            mp.stop();
            mp.release();
            mp= null;
        }
    }
}
