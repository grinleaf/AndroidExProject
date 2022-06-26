package com.grinleaf.ex081httprequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.grinleaf.ex081httprequest.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //1. 뷰바인딩을 통해 최상위 뷰로 화면 보여주기
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //2. 버튼 기능 추가
        binding.btnGet.setOnClickListener(v->clickGet());
        binding.btnPost.setOnClickListener(v->clickPost());
    }

    //3. 호출할 메소드 작성 : GET   --> 이 작업 이후 Visual Studio Code 로 가서 php 프로그램 작성 (html 은 웹용이므로 필요x)
    void clickGet(){
        //3-1. 네트워크 작업은 반드시 별도 Thread 가 작업함
        new Thread(){
            @Override
            public void run() {
                //3-2. 서버에 보낼 데이터들
                String title= binding.etTitle.getText().toString();
                String msg= binding.etMsg.getText().toString();

                //3-3. GET 방식으로 보낼 서버의 주소(URL) : 닷홈 내 서버(=호스팅 서버)/파일경로(=폴더명/파일명)
                String serverUrl= "http://grinleaf.dothome.co.kr/03AndroidHttpRequest/getTest.php";
                
                //3-4. GET 방식은 URL 뒤에 ? 를 붙이고 요청 파라미터 값들(title, message)을 전송함
                //--> 보안 취약. 드러나도 상관없는 정보 전송시에만 사용
                //--> 한글 및 특문 깨짐. 한글을 URL 에 사용할 수 있도록 암호화(인코딩)할 것

                //3-5. 인코더(Encoder) : title 변수 안의 값을 인코딩하고, 다시 title 변수에 대입 + 예외처리
                try {
                    title= URLEncoder.encode(title, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String getUrl= serverUrl+"?title="+title+"&msg="+msg;

                //3-6. 서버와 연결
                try {
                    URL url= new URL(getUrl); //이미 GET 방식은 보낼 데이터를 URL 뒤에 붙였기 때문에, 별도의 OutputStream 이 없어도 됨.

                    //다만, 서버(getTest.php)의 프로그램에서 응답된(echo) 글씨를 읽어오기 위해 InputStream 열기
                    InputStream is= url.openStream();                   //바이트로 읽어옴
                    InputStreamReader isr= new InputStreamReader(is);   //한글자씩 읽어옴
                    BufferedReader reader= new BufferedReader(isr);     //한글자들을 쌓음

                    StringBuffer buffer= new StringBuffer();

                    while(true){
                        String line= reader.readLine();     //BufferReader 객체에 한글자씩 쌓인 데이터를 한줄씩 읽어오기
                        if(line==null) break;

                        buffer.append(line+"\n");   //버퍼에 한줄씩 쌓기
                    }

                    runOnUiThread(()->{     //람다식표기법 : run()
                        binding.tv.setText(buffer.toString());
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //6. 호출할 메소드 작성 : POST     --> 이후 VSC 로
    void clickPost(){
        //6-1. 네트워크 작업을 위한 스레드 생성
        new Thread(){
            @Override
            public void run() {
                //6-2. 서버로 보낼 메세지
                String id= binding.etTitle.getText().toString();
                String pw= binding.etMsg.getText().toString();

                //6-3. POST 방식으로 보낼 서버 주소
                String serverUrl= "http://grinleaf.dothome.co.kr/03AndroidHttpRequest/postTest.php";

                //6-4. 서버에 연결 (url 주소, 인코딩 필요 x)
                try {
                    URL url= new URL(serverUrl);
                    //서버에 데이터를 보내기 위해 OutputStream 이 필요 //** URL 은 InputStream 만 열 수 있음! **
                    //HTTP 통신 규약에 따라 데이터 주고받는 역할, URL 객체의 조수역할을 하는 객체 존재 : HttpURLConnection (앞으로 URL 대신 요거 쓰기로 한다!★)
                    // --> URL 객체에서 커넥션 기능을 받아옴 + 예외처리 + 형변환
                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");    //반드시 대문자쓸 것!
                    connection.setDoOutput(true);   //OutputStream 만 열 수 있음
                    connection.setDoInput(true);    //InputStream 만 열 수 있음
                    connection.setUseCaches(false); //구글 권장사항 (메모리 낭비 방지, 보안 등의 이유)

                    OutputStream os= connection.getOutputStream();
                    PrintWriter writer= new PrintWriter(os);    //보조 문자스트림

                    //서버로 보낼 데이터들을 특정 포맷으로 만들기
                    String data= "id="+id+"&pw"+pw;
                    writer.print(data);     //이 때 딱 데이터가 전송됨
                    writer.flush();
                    writer.close();

                    //서버(postTest.php)의 프로그램으로부터 응답된 결과 받기
                    InputStream is= connection.getInputStream();
                    InputStreamReader isr= new InputStreamReader(is);
                    BufferedReader reader= new BufferedReader(isr);

                    StringBuffer buffer= new StringBuffer();
                    while(true){
                        String line= reader.readLine();
                        if(line==null) break;

                        buffer.append(line+"\n");
                    }

                    runOnUiThread(()-> binding.tv.setText(buffer.toString()));

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}