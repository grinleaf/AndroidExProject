package com.grinleaf.ex075alarmmanager;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //2-8. 알림 만들기
        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(this);   //Service 는 Context 를 상속받았으므로 context 에 그냥 this 줄수 있음

        NotificationChannelCompat channel= new NotificationChannelCompat.Builder("ch1",NotificationManagerCompat.IMPORTANCE_HIGH).setName("Alarm Channel").build();
        notificationManagerCompat.createNotificationChannel(channel);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(this, "ch1");

        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setContentTitle("My Alarm");
        builder.setContentText("알람이 시작되었습니다.");

        Intent i= new Intent(this, AlarmActivity.class);    //알람 액티비티 실행
        //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 200, i, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);
        builder.addAction(R.drawable.ic_stat_name,"확인",pendingIntent);

        //2-9. 포어그라운드 서비스로 동작한다는 기능 호출 - [ 포어그라운드 퍼미션 호출 필요 ]
        startForeground(12, builder.build());
        return START_STICKY;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(this);
        stopForeground(true);
    }
}
