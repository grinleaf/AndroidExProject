package com.grinleaf.ex050threadnetworkimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb= findViewById(R.id.pb);
        iv= findViewById(R.id.iv);
        //2. 버튼에 기능 부여 : MainThread 는 네트워크 작업을 할 수 없게 되어있음! 별도의 Thread 생성하여 네트워크 작업을 수행하도록 함
            //네트워크 작업을 할 때는 반드시 해당 앱에서 인터넷을 사용한다는 허가(퍼미션)을 받아야함 --> AndroidManiFest.xml 문서에서 받을 수 있음
        findViewById(R.id.btn).setOnClickListener(view -> {
            //4. 별도 스레드를 바로 만들어서 사용하는 방법
            //new Thread().start();     //요 형태로 작성. Thread() 뒤에 {} 붙이고 run() 메소드 오버라이드
            new Thread(){
                @Override
                public void run() {

                    //11-1. Progress Bar 부터 보이도록 이미지 코드 앞에 배치
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb.setVisibility(View.VISIBLE);
                        }
                    });

                    //5. Network 상에 있는 이미지의 주소(url) 가져오기
                    String imgUrl= "https://cdn.pixabay.com/photo/2017/05/25/09/22/flower-2342706__340.jpg";
                    
                    //6. 서버 이미지 주소까지 연결되는 다리(Stream=무지개로드) 열기 : 다리를 열어주는 역할을 하는 URL 객체(해임달) 필요 + 반드시 예외처리
                    try {
                        URL url= new URL(imgUrl);   //요거 작성
                        //6-1. 스트림은 url 이 만들 수 있음 + 반드시 예외처리
                        InputStream is= url.openStream();
                        
                        //6-2. 스트림을 통해 이미지파일을 읽어온 후, 이미지뷰에 설정하기
                        // 6-2-a. 안드에서 이미지파일을 가지는 클래스 [ Bitmap ] 객체 생성
                        // 6-2-b. 비트맵 객체를 만들어, 이미지파일을 해독하여 비트맵이미지로 만들어주는 [ BitmapFactory.decodeStream(inputStream) ]
                        final Bitmap bm= BitmapFactory.decodeStream(is);
                        //iv.setImageBitmap(bm);    //error. 7. 별도의 스레드는 UI 변경 작업이 불가능하므로, UI 스레드(MainThread) 에서 실행되도록 코드 작성
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //해당 영역 안에서 UI 변경작업이 가능해짐
                                iv.setImageBitmap(bm);  //요거 원래는 에러얌. 익명클래스 안에서는 지역변수 사용 불가능(편집기가 변수 bm 을 알아서 final 로 바꿔줌)
                                
                                //11-2. 이미지 설정이 완료되면 프로그래스 바가 사라지도록 함
                                pb.setVisibility(View.GONE);
                            }
                        });

                        // 6-2-c. 작업 끝마치면 열려있는 stream 닫기!
                        is.close();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        });

        findViewById(R.id.btn2).setOnClickListener(view -> {
            //9. 이미지 로드 외부 라이브러리(대표적인 2가지) 이용한 이미지 설정 : [ Picasso ] / [ Glide(구글 소유) ]
            String imgUrl= "https://cdn.pixabay.com/photo/2017/05/08/13/15/bird-2295436__340.jpg";   //다른 이미지
            //Glide.with(this).load(imgUrl).into(iv); //한~줄~컷 = =... 물론 이 라이브러리는 이미지 가져올 때만 쓸 수 있기 때문에, 다른 형식의 파일은 스레드 직접 써야댕
            //Glide.with(this).load(R.drawable.koala).into(iv);   //내가 가진 이미지 가져오기도 가능. (이미지의 해상도에 맞춰 메모리 관리도 자동으로 해주기 때문에, 용량 문제가 있을 때 쓰면 유용함)

            //10. gif 파일도 표현 가능!
            //iv.setImageResource(R.drawable.moana);  //요건 멈춘 이미지로 보임
            Glide.with(this).load(R.drawable.moana).into(iv);

        });

    }
}