package com.grinleaf.ex055datastorageinternal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et= findViewById(R.id.et);
        tv= findViewById(R.id.tv);

        findViewById(R.id.btn_save).setOnClickListener(v->saveData());
        findViewById(R.id.btn_load).setOnClickListener(v->loadData());
    }

    void saveData(){        //데이터 가져오기 -> 바이트데이터 -> 보조문자스트림
        //저장할 데이터 가져오기
        String data= et.getText().toString();
        et.setText(""); //edittext 초기화
        
        //액티비티 클래스는 이미 내장메모리(Internal)에 File 을 저장할 수 있도록 Stream 을 열 수 있는 기능 메소드가 존재함
        try {
            FileOutputStream fos= openFileOutput("Data.txt",MODE_APPEND); //MODE_APPEND : 데이터 누적 / MODE_PRIVATE : 접근제한, 데이터 덮어쓰기
            //fos 는 바이트 단위로 데이터를 보내야 함. 사용하기 불편하므로, 문자단위 스트림으로 변환 --> 더 나아가, 보조문자스트림을 쓰면 편해짐!
            PrintWriter writer= new PrintWriter(fos);

            //마치 JAVA 의 System.out.println() 기능처럼 파일에 데이터를 쓰는 방식
            writer.println(data);
            writer.flush();
            writer.close();

            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();

        //세이브 버튼 클릭 시 데이터 저장경로 : Device File Explorer - data 폴더 - data 폴더 - android...Ex055..프로젝트명 폴더 - files 폴더 안에 txt 지정한 파일명.확장자명 으로 저장됨
        //이 저장소 공간은 앱을 삭제하면 사라진다. 그 때 백지가 됨...앱 종료 시에는 데이터가 남아있음!
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    void loadData(){        //데이터 읽어오기 -> 바이트데이터 -> 문자스트림 -> 보조문자스트림
        try {
            FileInputStream fis= openFileInput("Data.txt");
            //바이트단위로 읽어들인 데이터를 문자스트림으로 변환하기
            InputStreamReader isr= new InputStreamReader(fis);
            //문자스트림(isr)은 한 번에 한글자씩 읽어오기 때문에 사용이 불편함. 한 줄 씩 읽어들이는 능력을 가진 보조문자스트림으로 변환하기
            BufferedReader reader= new BufferedReader(isr);

            StringBuffer buffer= new StringBuffer();    //데이터를 쌓아두는 역할

            while(true) {
                String line= reader.readLine(); //한줄읽기 --> 줄바꿈 문자는 빼고 읽어옴 (따로 \n 처리를 해줘야함)
                if(line==null) break;
                buffer.append(line+"\n");    //StringBuffer 에 읽어들인 한줄을 쌓기
            }

            tv.setText(buffer.toString());

            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}