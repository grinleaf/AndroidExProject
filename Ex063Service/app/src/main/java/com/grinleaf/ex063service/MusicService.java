package com.grinleaf.ex063service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

//안드로이드 4대 컴포넌트는 AndroidManifest.xml 에 반드시 등록해야 사용이 가능함!
public class MusicService extends Service {

    MediaPlayer mp;

    //startService()/startForegroundService() 명령으로 해당 Service 객체가 생성되어 시작되면 자동으로 발동하는 라이프사이클 콜백메소드 : [ onStartCommand() ]
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //startForegroundService() 로 실행되는 서비스는 반드시 이 메소드를 호출해야 함 [ startForeground() ] --> foreground service 로 동작
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder= null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){   //26 버전 이후
            NotificationChannel channel= new NotificationChannel("ch1","Ex063Service App 채널#1",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);

            builder= new NotificationCompat.Builder(this,"ch1");
        }else{  //26버전 이전
            builder= new NotificationCompat.Builder(this,"");
        }

        builder.setSmallIcon(R.drawable.ic_noti);
        builder.setContentTitle("Music Service");
        builder.setContentText("뮤직서비스가 실행됩니다.");

        //알림창을 클릭했을 때, 뮤직서비스를 종료하는 버튼(UI)을 가진 MainActivity 실행하도록 보류중인 Intent 제작
        Intent i= new Intent(this,MainActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,100,i,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);

        Notification notification= builder.build();
        startForeground(1,notification);  //요게 없으면 동작 x. 알림 공지됨 (필수)

        //MediaPlayer 객체 생성 및 실행
        if(mp==null){
            mp= MediaPlayer.create(this,R.raw.kalimba);    //파라미터 (context-Service 클래스는 Context 상속관계,리소스파일 경로)
            mp.setVolume( 0.7f,0.7f); //디바이스 볼륨 X, 소프트웨어 자체 볼륨 O
            mp.setLooping(true);
        }

        mp.start(); //시작 or 이어하기

        return START_STICKY;    //메모리문제로 프로세스가 강제로 Kill 되는 경우 존재 (메모리 문제 발생 시 백그라운드 프로세스부터 강제로 멈추곤 함. Activity 는 후순위)
                                //메모리문제가 해결되었을 때, 자동으로 다시 서비스가 실행될지 여부를 결정하는 것!
                                //START_STICKY = 자동 재실행 / START_NOT_STICKY = 메모리 여유공간이 생겨도 다시 실행되지 않음
    }

    //stopService() 명령으로 이 Service 객체가 종료되면 자동으로 발동하는 라이프사이클 콜백메소드 : [ onDestroy() ]
    @Override
    public void onDestroy() {
        //MediaPlayer 종료
        if(mp!=null){
            mp.stop();
            mp.release();   //MediaPlayer 는 할당되는 메모리 영역이 달라서 따로 release 해줘야 가비지 컬렉터가 참조 끊겼을 때 지워줄 수 있음 (메모리 누수 방지)
            mp= null;   //종료 후 객체 비우기
        }

        //포어그라운드 서비스를 멈추도록 하면, 알림도 자동으로 제거되도록 설정
        stopForeground(true);
        
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
