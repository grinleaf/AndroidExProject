package com.grinleaf.ex075alarmmanager;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

//1-2. 알람리시버 클래스 --> 4대 컴포넌트는 Manifest.xml 에 추가할 것!
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "alarm receive", Toast.LENGTH_SHORT).show();
        //1-8. 알람 receive 시 기존에는 새로운 액티비티(알람화면)를 보여줌
        //--> 1-9 AlarmActivity 생성 (activity_alarm.xml 로)
        //1-10. 알람화면 객체 보내는 Intent 생성
        //Intent i= new Intent(context, AlarmActivity.class);
        //context.startActivity(i); //버전업되면서 알람화면(액티비티)를 보여주는 것은 제한되는 중 ㅠ

        //1-11. Notification (알림) 보이기
        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(context);

        NotificationChannelCompat channel= new NotificationChannelCompat.Builder("ch1",NotificationManagerCompat.IMPORTANCE_HIGH).setName("Alarm Channel").build();  //두번째 파라미터 importance : HIGH 는 소리도나고 알림도 뜸
        notificationManagerCompat.createNotificationChannel(channel);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"ch1");

        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setContentTitle("MyAlarm");
        builder.setContentText("알람이 시작되었습니다.");

        Intent i= new Intent(context, AlarmActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 20,i,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);
        builder.addAction(R.drawable.ic_stat_name,"확인",pendingIntent);

        notificationManagerCompat.notify(11,builder.build());
    }
}
