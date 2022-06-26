package com.grinleaf.ex061broadcastreceiverbooting;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

//BroadcastReceiver 은 반드시 AndroidManifest.xml 에 등록해야함
public class BootingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        
        //N(누가) 버전(=마시멜로우 버전) 부터는 앱 설치 후 적어도 1회 사용자가 직접 런처화면(앱목록화면=메뉴)에서 앱 아이콘을 클릭하여 실행한 앱에 한해 BOOT_COMPLETED 에 대한 방송 수신이 가능함
            //앱 설치후 1회도 클릭되지 않은 앱 = 악성 앱의 가능성. 사용자가 설치를 원하지 않았을 가능성이 있는 앱이라고 판단, 이 앱이 디바이스 켜져있을 때 자동으로 계속 실행되는 것을 제한함
        
        //Manifest.xml 에서 <receiver> 의 <action> 은 여러 개가 올 수 있으므로, 어떤 액션을 가리키는지를 확인하는 구문
        String action= intent.getAction();
        //수신한 방송이 "BOOT_COMPLETED" 인지 확인
        if(action.equals(Intent.ACTION_BOOT_COMPLETED)){    //"android.permission.RECEIVE_BOOT_COMPLETED"를 비교값으로 넣어도 됨
            Toast.makeText(context, "boot received", Toast.LENGTH_SHORT).show();
            
            //보통은 부팅완료되었을 때 요기 영역에 백그라운드 작업을 수행하는 Service 코드를 작성하여 실행하는 것이 일반적 ! ex) 채팅앱, 시스템앱(시계 앱) 등
            //해당 예제에서는 부팅완료되면 MainActivity 를 실행하는 것으로 실습 확인해봅시당~~
                //android 10(api 29) 버전부터는 리시버에서 직접 액티비티를 실행하는 것을 금지시키고(사용자가 원치 않음에도 부팅만 되면 앱이 실행되도록 만든 앱들 방지)
                //대신 리시버에서 알림(Notification)을 띄우고, 이 알림을 사용자가 클릭해야만 액티비티가 실행되도록 강제함
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){

                    //알림관리자 객체부터 (OS 가 가진 기능) 소환
                    NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);     //SystemService = 운영체제가 가진 모든 서비스(알람, 블루투스, 배터리 기능 등)
                    
                    //알림객체를 만들어주는 빌더 객체 생성 전에, 알림 채널 객체부터 생성! --> 알림 창 꾹누르면 나오는 알림 상태 설정창
                    NotificationChannel channel= new NotificationChannel("ch1","부팅완료 리시버 앱", NotificationManager.IMPORTANCE_HIGH);
                    //채널을 알림시스템에 등록하기
                    notificationManager.createNotificationChannel(channel);
                    //알림 객체를 만들어 주는 건축가 객체 생성
                    Notification.Builder builder= new Notification.Builder(context,"ch1");  //빌더 객체와 알림 채널 객체 연동
                    
                    //빌더를 통해 원하는 알림 설정 : 상태창에 뜰 아이콘설정
                    builder.setSmallIcon(R.drawable.ic_noti);

                    builder.setContentTitle("부팅이 완료되었습니다.");
                    builder.setContentText("이제 Ex061 앱의 MainActivity 를 실행할 수 있습니다.");
                    builder.setSubText("앱 실행");

                    //알림창을 클릭하면 실행될 Activity 를 실행시켜주는 Intent 객체 생성
                    Intent i= new Intent(context, MainActivity.class);
                    //당장 실행하지 않고, 알림창을 클릭할 때까지 보류하기 위해 보류중인 인텐트로
                    PendingIntent pendingIntent= PendingIntent.getActivity(context, 100, i, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);

                    builder.setContentIntent(pendingIntent);
                    builder.setAutoCancel(true);    //알림창 클릭 시 알림창이 캔슬됨(지워짐)
                    Notification notification= builder.build();

                    //알림 매니저에게 알림 요청
                    notificationManager.notify(100,notification);

                }else{
                    Intent intent1= new Intent(context, MainActivity.class);
                    //단, 새로운 Activity 를 기존 Activity 가 아닌 곳에서 실행하려면, 새로운 task(=새로운 MainThread) 에서 실행되도록 해야함!
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                };
        }
    }
}
