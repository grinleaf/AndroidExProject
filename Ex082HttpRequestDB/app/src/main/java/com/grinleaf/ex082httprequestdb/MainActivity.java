package com.grinleaf.ex082httprequestdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;
import com.grinleaf.ex082httprequestdb.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSave.setOnClickListener(v->saveData());
        binding.btnLoad.setOnClickListener(v->loadData());
    }

    void saveData(){

        //소프트 키패드 닫기
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        //첫번째 파라미터 windowToken : 화면에서 포커스를 가질 뷰에 대한 권한 (=Token). 즉, 현재 포커스를 가지고 있는 것을 값으로 줄 것

        new Thread(){
            @Override
            public void run() {
                String title= binding.etTitle.getText().toString();
                String message= binding.etMsg.getText().toString();

                String serverUrl= "http://grinleaf.dothome.co.kr/03AndroidHttpRequest/insertDB.php";

                try {
                    URL url= new URL(serverUrl);

                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);

                    //보낼 데이터 포맷
                    String data= "title="+title+"&msg="+message;

                    OutputStream os= connection.getOutputStream();
                    PrintWriter writer= new PrintWriter(os);
                    writer.print(data);
                    writer.flush();
                    writer.close();
                    
                    //insertDB.php 로부터 DB 저장 결과를 응답받아 SnackBar or Toast 로 보여주기
                    InputStream is= connection.getInputStream();
                    InputStreamReader isr= new InputStreamReader(is);
                    BufferedReader reader= new BufferedReader(isr);

                    StringBuffer buffer= new StringBuffer();
                    while(true){
                        String line= reader.readLine();
                        if(line==null) break;

                        buffer.append(line+"\n");
                    }

                    //스낵바 or 토스트
                    runOnUiThread(()-> {
                        Snackbar.make(binding.getRoot(),buffer.toString(),Snackbar.LENGTH_SHORT).show();

                        binding.etTitle.setText("");
                        binding.etMsg.setText("");
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    void loadData(){
        //DB 데이터를 읽어서 보여주는 화면으로 이동 - 리사이클러뷰를 가진 액티비티
        startActivity(new Intent(MainActivity.this,BoardActivity.class));
    }
}