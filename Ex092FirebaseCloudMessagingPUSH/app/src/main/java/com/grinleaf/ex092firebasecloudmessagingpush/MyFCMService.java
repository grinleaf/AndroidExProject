package com.grinleaf.ex092firebasecloudmessagingpush;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFCMService extends FirebaseMessagingService {
    
    //4-3. 앱이 설치된 후 적어도 한번은 앱 런처화면(앱 목록화면)에서 사용자가 직접 아이콘을 클릭하여 실행한 앱만 push 메시지를 받을 수 있음! --> 악성 앱에 대한 보안
    
    //4. FCM 메시지 유형 2가지 (포그라운드/백그라운드 상태에 따라 알림, 데이터 메시지 형태가 다름 ★)
    //4-1. 알림 메시지
    //4-1-a. 앱이 Foreground 일 때 : 무조건 onMessageReceived() 메소드가 발동함
    //--> FCM 메시지를 받았을 때 자동으로 발동하는 메소드 : 앱이 켜져있는 동안에는 무조건 발동
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Log.i("TAG","onMessageReceived.....");  //msg 가 뜨면, 메시지를 받은 상태인 것!

        //6-2. 알림 메시지 타입일 경우, 해당 메소드의 파라미터로 전달된 RemoteMessage message 에 알림 정보(=전달된 데이터)가 있음
        RemoteMessage.Notification notiMsg= message.getNotification();
        if(notiMsg!=null){
            String title= notiMsg.getTitle();
            String text= notiMsg.getBody();

            Log.i("TAG",title+"..."+text);
        }
        //알림 메시지 유형은 백그라운드 상태일 때, 기본 알림으로 발동하기 때문에 잘 사용하지 않음! 데이터 메시지 유형을 더 많이 사용
        //알림작성기 라는 테스트 push 서비스는 알림 + 데이터 유형은 가능함!
        //6-3. 만약, 데이터 메시지 유형일 경우 (푸시 메시지 작성 시 추가 데이터를 함께 보냄)
        Map<String, String> data= message.getData();    //Map 방식으로 받아오기
        if(data.size()>0){
            String name= data.get("name");
            String msg= data.get("msg");

            //push 메시지 수신 알림(Notification)
            NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(this);
            NotificationChannelCompat channel= new NotificationChannelCompat.Builder("fcm_ch",NotificationManagerCompat.IMPORTANCE_HIGH).setName("Ex92 FCM CHANNEL").build();
            notificationManagerCompat.createNotificationChannel(channel);
            NotificationCompat.Builder builder= new NotificationCompat.Builder(this,"fcm_ch");

            builder.setSmallIcon(R.drawable.ic_noti).setContentTitle(name).setContentText(msg);

            notificationManagerCompat.notify(11,builder.build());
        }

        //6-1. 사용자에게 push 메시지의 수신을 알려주기 위해 알림(Notification) 보이기
//        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(this);
//        NotificationChannelCompat channel= new NotificationChannelCompat.Builder("fcm_ch",NotificationManagerCompat.IMPORTANCE_HIGH).setName("Ex92 FCM CHANNEL").build();
//        notificationManagerCompat.createNotificationChannel(channel);
//        NotificationCompat.Builder builder= new NotificationCompat.Builder(this,"fcm_ch");
//
//        builder.setSmallIcon(R.drawable.ic_noti).setContentTitle("aaa").setContentText("bbbbb");
//
//        notificationManagerCompat.notify(11,builder.build());
    }
    //4-1-b. 앱이 Background 일 때 : 스테이터스바에 기본 알림 아이콘이 나타남

    //4-2. 데이터 메시지
    //4-2-a. 앱이 Foreground 일 때 : 무조건 onMessageReceived() 메소드가 발동함
    //4-2-b. 앱이 Background 일 때 : onMessageReceived() 메소드가 발동함

    ////////////////////////////////////////////////////////////////////////////////
    //  **가이드 문서 설명**                                                         //
    //  앱 상태	알림	                데이터	            모두                       //
    //  포그라운드	onMessageReceived	onMessageReceived	onMessageReceived     //
    //  백그라운드	작업 표시줄	        onMessageReceived	알림: 작업 표시줄        //
    //                                                      데이터: 인텐트 부가 정보  //
    ////////////////////////////////////////////////////////////////////////////////
    //** firebase console 에 알림메시지 수신을 연습(테스트)할 수 있도록 '알림작성기' 가 존재함 --> '알림메시지 유형' 만 가능함. '데이터메시지 유형' 은 테스트불가
    //** 대신 '알림메시지 유형' + '데이터 메시지 유형' 은 테스트 가능함!

    //6. 디바이스에서 알림 클릭 시 메인 액티비티만 실행. 보통 실제 앱에서 기본 알림 유형을 사용하는 일이 드물다!
    //무조건 메시지를 onMessageReceived() 메소드에서 받아서 처리하는 방법을 선호함. 개발자가 작성한 Notification 으로 보여주기 가능!

    //1. FCM 서버에서 디바이스 고유 등록 ID(=token =디바이스 식별자)이 자동으로 발동하는 메소드 --> [ onNewToken() ]
    //--> manifest.xml 에 service 등록이 되어있다면, 앱을 실행하기만 해도 자동으로 실행된다.
    //--> 최초 한 번 설치(연동한 서버에 프로젝트 인식)시에만 발동(토큰 생성)함 - 다시 보려면 앱 삭제/설치
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        //2. 발급되어있는 Token 확인 : Third Party Server(=dothome 서버 같은 호스팅 웹서버) 의 DB 에서 토큰의 식별자를 가져오기
        Log.i("TOKEN",token);   //하단 Logcat 에서 식별자 "TOKEN" 으로 검색. I/TOKEN : 뒤의 문자열 전부가 token!
    }
}
