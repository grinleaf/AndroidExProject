package com.grinleaf.ex082httprequestdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.os.Bundle;

import com.grinleaf.ex082httprequestdb.databinding.ActivityBoardBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {

    ActivityBoardBinding binding;

    ArrayList<Item> items= new ArrayList<>();
    BoardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_board);
        binding= ActivityBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter= new BoardAdapter(this, items);
        binding.recycler.setAdapter(adapter);

        //SwipeRefreshLayout 이 발동하는 것을 듣는 리스너
        binding.layoutRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //리프레쉬 되었을 때, 서버 데이터를 다시 읽어오기
                loadData();

                //돌고있는 새로고침 아이콘을 사라지게 하기
                binding.layoutRefresh.setRefreshing(false);
            }
        });


        //서버 DB 의 데이터를 읽어와서 items 에 추가하는 기능 메소드 호출
        loadData();
    }

    //서버 DB 의 데이터를 읽어와서 items 에 추가하는 기능 메소드 작성
    void loadData(){
        //테스트 목적으로 가상의 Item 객체 추가
//        items.add(new Item(1, "aaa","bbb","2022"));
//        adapter.notifyDataSetChanged();
        
        items.clear();      //기존에 있던 데이터 (카드뷰)들을 제거하고 load 작업 시작함 (refresh 를 위함)

        //서버에서 DB 값을 echo 시켜주는 php 문서 실행 (데이터 가져오기 - output 필요x )
        new Thread(){
            @Override
            public void run() {
                String serverUrl="http://grinleaf.dothome.co.kr/03AndroidHttpRequest/loadDB.php";

                try {
                    URL url= new URL(serverUrl);

                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.setUseCaches(false);

                    InputStream is= connection.getInputStream();
                    InputStreamReader isr= new InputStreamReader(is);
                    BufferedReader reader= new BufferedReader(isr);

                    StringBuffer buffer= new StringBuffer();
                    while(true){
                        String line= reader.readLine();
                        if(line==null) break;
                        buffer.append(line+"\n");
                    }

                    //우선은 잘 읽어왔는지 확인하기 위해 다이얼로그로 보여주기
//                    runOnUiThread(()->{
//                        new AlertDialog.Builder(BoardActivity.this).setMessage(buffer.toString()).create().show();
//                    });

                    //서버에서 echo 된 문자열 데이터에서 '&' 문자를 기준으로 문자열을 분리시키기
                    /*
                        String s= "aaa&bbb&";
                        String[] aaa= s.split("&"); //aaa 의 배열 요소는 총 3개가 됨. 마지막 & 뒤에도 "" 빈문자가 있는 것으로 인식함
                    */
                    String[] rows= buffer.toString().split("&");

                    for(String row: rows){

                        //한줄 row 안에 "," 구분자로 구분된 값들이 여러개 있음. (4개)
                        String[] datas= row.split(",");
                        if(datas.length!=4) continue;

                        int no= Integer.parseInt(datas[0]);
                        String title= datas[1];
                        String msg= datas[2];
                        String date= datas[3];

                        items.add(0, new Item(no,title,msg,date));
                    }
                    runOnUiThread(()->adapter.notifyDataSetChanged());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}