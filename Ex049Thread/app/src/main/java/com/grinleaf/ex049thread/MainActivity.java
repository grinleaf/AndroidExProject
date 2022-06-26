package com.grinleaf.ex049thread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    int num=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv= findViewById(R.id.tv);

        findViewById(R.id.b1).setOnClickListener(view -> {      //버튼 참조변수 없이 + 람다식 표현으로 리스너 객체 생성하기
        // 오래 걸리는 작업 유도하기 : 실무에서는 네트워크 작업/파일입출력/DB 작업 등이 여기 해당함! 파일입출력은 처리속도가 빠르고, DB 작업도 CPU 성능 평균이 올라서 스레드 쓸 필요가 없음. 보통 네트워크 작업
            //2. 별도의 Thread 를 사용하지 않고 코드 작성 --> [ MainThread 가 처리 ]
            for(int i=0; i<20; i++){
                num++;
                //3. 화면에 num 값 출력하기
                    // 이 때, setText() 가 실행되는 순간 화면에 글씨가 나타나는 것이 아니라, setOnClick...() 작업이 모두 완료되어야 화면이 바뀜.
                    // 즉, 반복문이 끝나기 전에 화면이 바뀌지 x
                tv.setText(num+""); //String 값을 파라미터로 줘야하므로 빈문자열 더해주기
                
                //4. 0.5 초간 Thread 를 대기시키기 : [ Thread.sleep(); ] --> 반드시 예외처리(try/catch)
                try {
                    Thread.sleep(500);  //요거 작성!
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }   //실행 : b1 버튼 클릭하고 대기 중인 상태에서 두번째 버튼을 누르면 ANR 에러 다이얼로그가 뜸! Exit/Wait
            }
        });

        findViewById(R.id.b2).setOnClickListener(view -> {
        //오래 걸리는 작업 유도하기 2
            //6-3. 오래 걸리는 작업을 별도의 직원(Thread=MyThread) 에게 맡기기 : Thread 객체 생성 및 실행
            MyThread t= new MyThread();
            t.start();

        });
    }//onCreate() method

    //6-1. inner class : 사용자정의 Thread 클래스 생성
    class MyThread extends Thread{
        //6-2. 해당 스레드가 작업할 내용을 작성하는 메소드 : [ run() ] --> 이 메소드 안에 작성해야만 별도 Thread 가 처리해줌 (필수 메소드)
        @Override
        public void run() {
            //6-4. 오래걸리는 작업 유도하기
            for(int i=0;i<20;i++){
                num++;

                //tv.setText(num+"");
                //7. 화면 갱신 작업 : error 발생! --> 안드로이드는 UI(화면) 를 변경하는 작업은 반드시 MainThread 가 하도록 강제함( MainThread= [ UI(전용)Thread ] ).
                    // 제한 때문에 별도 스레드가 화면을 변경하려는 코드는 에러. 그럼에도 별도 스레드가 화면을 건드려야하는 상황일 경우, MainThread 에게 UI 변경작업을 요청해야함
                // 방법 1) Handler 를 이용 : 7-1. 하단 멤버변수 위치에 Handler 객체 생성하기(별도 스레드 생성)
                    // MainThread 에게 별도 스레드들이 작업 확인을 위해 계속 접근(Message 전달)함
                    // --> MessageQueue(메시지통)를 따로 두고, 이 메시지통을 관리하는 알바생(Looper)를 두고, Message 가 왔는지 여부를 MainThread 에 전달(Handler)하도록 함
                    // --> 이 때, Handler 는 별도 스레드가 맞지만, MainThread 에게 위임장을 받아 UI 를 변경할 수 있는 능력이 있음!

                //7-2. [ .sendEmptyMessage() ]
                    //handler.sendEmptyMessage(0);    //파라미터는 필요할 때 사용함. 별 의미는 없옹


                // 방법 2) Activity 클래스의 메소드를 이용 : MainThread 의 능력 [ runOnUiThread() ] --> UI 스레드(=MainThread) 위에서 실행하는 메소드
                    //8. 별도의 스레드 만들지 않고(클래스 작성x) 파라미터에 Runnable() 메소드 사용.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(num+"");
                    }
                });
                
                //0.5초 대기
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //7-1. 핸들러 객체 만들기 = 별도의 스레드
    Handler handler= new Handler(Looper.getMainLooper()){     //7-3. {} 를 붙여 콜백 메소드 작성하면, UI 변경 작업이 가능함 / 이 때, 파라미터에 알바생 Looper 를 주어야 함!
        //sendMessage() 를 이용하여 별도 스레드가 메인 스레드에게 UI 변경작업을 요청하면 자동으로 발동하는 콜백 메소드
        @Override
        public void handleMessage(@NonNull Message msg) {   //해당 영역 안에서 UI 작업(=화면 갱신 작업) 가능!
            tv.setText(num+"");
        }
    };

}//MainActivity class