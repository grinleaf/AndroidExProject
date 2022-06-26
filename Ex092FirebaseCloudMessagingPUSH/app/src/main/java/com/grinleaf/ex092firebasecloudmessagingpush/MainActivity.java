package com.grinleaf.ex092firebasecloudmessagingpush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.grinleaf.ex092firebasecloudmessagingpush.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    //Firebase Cloud Messaging : FCM push 서비스 - 앱이 꺼져있어도 서버에서 클라이언트에 메시지 전송

    //0. Firebase 연동

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(v->{
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task->{
                if(task.isSuccessful()){
                    String token= task.getResult();
                    //3. 실무에서는 해당 토큰을 웹서버(호스팅서버)로 전송해야함! 연습이니까 로그나 찍자
                    Log.i("TOKEN",token);
                    Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
                }
            });
        });

        //5. 임의로 구독/구독취소 버튼 설정
        binding.btn2.setOnClickListener(v->{
            FirebaseMessaging.getInstance().subscribeToTopic("FCM").addOnCompleteListener(task->{
               if(task.isSuccessful()) Toast.makeText(this, "FCM 메시지 수신을 동의하셨습니다.", Toast.LENGTH_SHORT).show(); 
            });
        });

        binding.btn3.setOnClickListener(v->{
            FirebaseMessaging.getInstance().unsubscribeFromTopic("FCM").addOnCompleteListener(task->{
               if(task.isSuccessful()) Toast.makeText(this, "FCM 메시지 수신을 취소하셨습니다.", Toast.LENGTH_SHORT).show();
            });
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }
}