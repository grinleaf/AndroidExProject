package com.grinleaf.ex051threadprogressdialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class MainActivity extends AppCompatActivity {
    
    //2. 참조변수 선언
    ProgressDialog dialog= null;
    int gauge= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.b1).setOnClickListener(view -> {
            if(dialog!=null) return;    //이미 dialog 가 만들어져 있으면 작동x

            //1. wheel type progress dialog 만들기 : 다운로드 하는 데이터의 양이 불명확하여 최대값을 알 수 없을 때!
            dialog= new ProgressDialog(this);
            dialog.setTitle("wheel Dialog");
            dialog.setMessage("downloading...");

            //1-1. 스타일 주기 --> 여기서 wheel(SPINNER)/bar(HORIZONTAL) 로 형태가 나뉨
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            //네트워크 작업 등의 코드가 작성될 영역 <-- 테스트용 지연 시간을 줍시당
            //2. Thread.sleep(3000); //이러면 메인스레드가 잠들어욧,,,, 스레드 객체 안만들었으니 --> Handler 를 활용할 것!
            handler.sendEmptyMessageDelayed(0,3000);
        });


        findViewById(R.id.b2).setOnClickListener(view -> {
            if(dialog!=null) return;

            //3. bar type progress dialog 만들기 : 다운로드 하는 데이터 양이 정량으로 최대값을 알 때.
            dialog= new ProgressDialog(this);
            dialog.setTitle("막대바 다이얼로그");
            dialog.setMessage("다운로드 중....");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

            //3-1. 게이지 최대값 설정 가능
            dialog.setMax(100);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            //3-2. 프로그래스 바의 채워진 게이지값 설정 가능
            dialog.setProgress(gauge);

            //3-3. 게이지를 증가시키는 별도의 스레드 만들기 --> 실무에서는 네트워크 작업 영역. 작업 완료량에 따른 게이지 증가
            new Thread(){
                @Override
                public void run() {
                    gauge= 0;

                    while(gauge<100){
                        gauge++;
                        dialog.setProgress(gauge);

                        try {
                            Thread.sleep(50);           //게이지 증가를 담당하는 별도 스레드에게 지연시간 부여
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    dialog.dismiss();
                    dialog= null;
                }
            }.start();

        });
    }

    //2. Handler 객체 생성 (멤버변수) + 바로 오버라이드
    Handler handler= new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //sendMessage() 를 통해 전달된 메시지가 처리될 때 자동으로 발동
            //sendMessageDelayed 를 통해 3초 뒤에 요 영역이 실행될 것! 여기가 dismiss()의 자리이다~!
            dialog.dismiss();
            dialog= null;   //다음 실행을 위해 변수를 비워줄 것
        }
    };

}