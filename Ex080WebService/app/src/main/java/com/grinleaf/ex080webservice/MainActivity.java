package com.grinleaf.ex080webservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.grinleaf.ex080webservice.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //activity_main.xml 과 연결되는 바인딩 클래스 참조변수
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(v->loadTextFile());
        binding.btn2.setOnClickListener(v->loadImageFile());


    }
    void loadTextFile(){
        //서버에서 제공하는 텍스트 문서 읽어오기 - 인터넷 퍼미션 필요
        //네트워크 작업은 오래걸리는 작업이므로 반드시 별도의 스레드가 수행한다 !
        Thread t= new Thread(){
            @Override
            public void run() {
                //텍스트문서가 있는 서버의 URL 주소
                String address= "http://grinleaf.dothome.co.kr/index.html"; //index.html 생략 가능
                //스트림을 연결해주는 URI/URL 객체
                try {
                    URL url= new URL(address);
                    InputStream is= url.openStream();   //InputStream 은 바이트단위 스트림
                    InputStreamReader isr= new InputStreamReader(is);   //바이트단위를 문자단위로 + 한글자씩 받아옴
                    BufferedReader reader= new BufferedReader(isr);     //한글자씩 받아오는 것을 여러글자씩 쌓는 것으로
                    
                    StringBuffer buffer= new StringBuffer();    
                    while(true){
                        String line= reader.readLine(); //얘만 만들어두면 line 에 있던 기존 값들은 계속 덮어씌워지므로, StringBuffer 이용
                        if(line==null) break;       //받아온 글씨가 더이상 없으면 while 문 탈출
                        buffer.append(line+"\n");   //데이터 쌓기
                    }

//                  //별도 스레드는 화면변경 작업을 할 수 없음!
//                  binding.tv.setText(buffer.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.tv.setText(buffer.toString());
                        }
                    });

                    reader.close();
                    
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

    }

    void loadImageFile(){

        //아래 과정을 전부 생략가능한 이미지 로드 라이브러리 : Glide
        String address= "http://grinleaf.dothome.co.kr/paris.jpg";
        Glide.with(this).load(address).into(binding.iv);

        //서버에서 이미지 파일을 읽어와서 이미지뷰에 보여주기
//        new Thread(){   //스레드 바로 생성하고 동작넣기~
//            @Override
//            public void run() {
//                String address= "http://grinleaf.dothome.co.kr/paris.jpg";
//                try {
//                    URL url= new URL(address);
//                    InputStream is= url.openStream();
//                    //이미지의 경우 안드로이드 클래스 중 이미지를 받는 Bitmap 으로 받기
//                    Bitmap bm= BitmapFactory.decodeStream(is);
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            binding.iv.setImageBitmap(bm);
//                        }
//                    });
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }.start();

    }
}