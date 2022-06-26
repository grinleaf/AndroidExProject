package com.grinleaf.ex062backgroundtaskbythread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_start).setOnClickListener(v->startTask());
        findViewById(R.id.btn_stop).setOnClickListener(v->stopTask());
    }
    
    //Service 컴포넌트를 사용하지 않고 백그라운드 작업을 했을 때의 문제점
    // : android 12(API 31) 버전 부터는 디바이스의 "back" 버튼 클릭 시 액티비티가 finish 되지 않고 홈 화면에 가려지기만 함 ( 앱 삭제해야 사라짐 )
    //   이전 버전들처럼 "back" 버튼 눌렀을 때 액티비티가 완전히 finish() 되도록 조정해야함! --> "뒤로가기"버튼 클릭시 자동으로 발동되는 콜백메소드 작성하기 [ onBackPressed() ]

    @Override
    public void onBackPressed() {   //뒤로가기버튼 두번눌러서 끄기, 뒤로가기버튼 누르면 정말 종료하시겠습니까 다이얼로그 띄우기 등의 작업을 작성하는 영역!
        //super.onBackPressed(); --> 요부분이 원래 12버전 전까지는 finish()였어서 신경쓸 필요가 없었는디 ㅠ
        finish();
    }


    //별도 Thread 로 백그라운드 작업 수행해보기

    MyThread myThread;

    void startTask(){
        if(myThread!=null) return;
        myThread = new MyThread();
        myThread.start();   //자동으로 해당 스레드의 run() 메소드 실행
    }
    
    void stopTask(){
        if(myThread!=null) {
            //myThread.stop();        //절대로 스레드를 직접 stop 명령으로 멈추면 안됨 --> 스레드의 종료는 run()메소드가 끝나면 자동으로 stop 됨
            myThread.isRun= false;  //while 문 때문에 run()이 끝나지 않기 때문에 종료하면 while 문을 빠져나오면 됨! 즉 while 문의 조건값을 false 로 바꿔주기
            myThread=null;          //참조주소 끊기
        }else{
            Toast.makeText(this, "Toast 를 참조하고 있지 않음", Toast.LENGTH_SHORT).show();
        }
    }

    //inner class////////////////////////////
    class MyThread extends Thread{
        boolean isRun= true;
        @Override
        public void run() {
            //5초마다 Toast 보여주는 동작 수행
            while(isRun){
                //별도의 스레드는 화면 변경작업 불가능. UI 스레드에게 넘겨야함
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "aaa", Toast.LENGTH_SHORT).show();
                    }
                });
                //5초 동안 잠시 대기
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /////////////////////////////////////////
}//MainActivity class